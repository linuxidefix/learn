package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by quyuanchao on 2019/6/30 15:31.
 * <p>Title: ElasticSearch Index</p>
 * <p>Description: </p>
 */
public class IndexApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws Exception {
        index();
//        index2();
//        index3();
//        index4();
//        indexWithSameDoc();
//        searchDate();
    }

    /**
     * 插入记录
     * 插入操作有四种方式，分同步异步操作，可选参数设置，结果返回IndexResponse，抛出异常
     *
     * @throws Exception
     */
    public static void index() throws Exception {

        //第一种方式: String
        IndexRequest request = new IndexRequest("posts", "doc", "1");
        String jsonString = "{" +
                "\"user\":\"aaa\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        //可选的参数设置
//        request.routing("routing");
//        request.parent("parent");
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.version(2);
//        request.versionType(VersionType.EXTERNAL);
//        request.opType(DocWriteRequest.OpType.CREATE);
//        request.opType("create");
//        request.setPipeline("pipeline");

        //同步执行
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //Index Response
        String index = indexResponse.getIndex();
        System.out.println("index = " + index);
        String type = indexResponse.getType();
        System.out.println("type = " + type);
        String id = indexResponse.getId();
        System.out.println("id = " + id);
        long version = indexResponse.getVersion();
        System.out.println("version = " + version);
        String response = indexResponse.toString();
        System.out.println("response = " + response);
        RestStatus status = indexResponse.status();
        System.out.println("status = " + status);

        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            // 文档第一次被创建时

        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            // 文档被更新时

        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            // 保存成功的分区小于总分区时

        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                // 处理潜在的失败
                String reason = failure.reason();
            }
        }
    }

    public static void index2() throws IOException {
        //第二种方式: Map
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "bbb");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "2").source(jsonMap);
        //异步执行
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("indexResponse.toString() = " + indexResponse.toString());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        };
        client.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
    }

    public static void index3() throws IOException {
        //第三种方式: XContentBuilder automatically converted to JSON
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "ccc");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "3")
                .source(builder);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        client.close();
    }

    /**
     * 使用字符串保存时间不会再es中进行时区转换，若使用Date类型保存时间，es会将该时间转换为格林尼治时间保存
     *
     * @throws IOException
     */
    public static void index4() throws IOException {
        //第四种方式: source -> key-pairs
        IndexRequest indexRequest = new IndexRequest("posts", "_doc", "4")
                .source("user", "ddd",
                        "postDate", "2019-07-21 17:24:59",
                        "message", "trying out Elasticsearch"
                );
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        client.close();
    }

    /**
     * 使用字符串查询时间不会在es中进行时区转换，若使用Date类型保存时间，es会将该时间转换为格林尼治时间保存
     *
     * @throws IOException
     */
    public static void searchDate() throws IOException {
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.rangeQuery("postDate").gte("2019-07-17 10:24:59"));
        searchSourceBuilder.query(QueryBuilders.rangeQuery("postDate").gte(new Date()));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        System.out.println("search = " + search);
    }

    public static void index5WithException() {
        IndexRequest request = new IndexRequest("posts", "doc", "1")
                .source("field", "value")
                .setIfSeqNo(100L)
                .setIfPrimaryTerm(1L);
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.CONFLICT) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建具有相同 index、type、id 的文档是会出现版本冲突
     *
     * @throws IOException
     */
    public static void indexWithSameDoc() throws IOException {
        IndexRequest request = new IndexRequest("posts", "doc", "1")
                // 指定该操作为创建类型
                .opType(DocWriteRequest.OpType.CREATE);
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        {
            xContentBuilder.field("user", "ccc");
            xContentBuilder.timeField("postDate", new Date());
            xContentBuilder.field("message", "trying out Elasticsearch");
        }
        xContentBuilder.endObject();
        request.source(xContentBuilder);
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println(" 发生了版本冲突 ");
            }
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
