package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.Map;

/**
 * Created by quyuanchao on 2019/6/30 17:03.
 * <p>Title: ElasticSearch Get Api</p>
 * <p>Description: </p>
 */
public class GetApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
//        get();
        get2();
    }

    public static void get() {
        try {
            GetRequest request = new GetRequest("posts", "docs", "10");
            // 同步获取数据
            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
            String index = getResponse.getIndex();
            System.out.println("index = " + index);
            String type = getResponse.getType();
            System.out.println("type = " + type);
            String id = getResponse.getId();
            System.out.println("id = " + id);
            if (getResponse.isExists()) {
                long version = getResponse.getVersion();
                System.out.println("version = " + version);
                String sourceAsString = getResponse.getSourceAsString();
                System.out.println("sourceAsString = " + sourceAsString);
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                sourceAsMap.forEach((key, value) -> System.out.println(key + ": " + value));
                long seqNo = getResponse.getSeqNo();
                System.out.println("seqNo = " + seqNo);
                String s = getResponse.toString();
                System.out.println("s = " + s);
            } else {
                // id或type不存在时的处理
                System.out.println("该文档不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                // index 不存在的时候抛出异常
                System.out.println(" 这个 index 不存在 ");
                e.printStackTrace();
            }
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void get2() {
        GetRequest request = new GetRequest("posts", "doc", "1");
        // 不拉取source，默认是enable
//        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        // 只拉取 message和Date结尾的fields
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, new String[]{"message", "*Date"}
                , Strings.EMPTY_ARRAY);
        request.fetchSourceContext(fetchSourceContext);
        // 异步获取数据
        client.getAsync(request, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(GetResponse response) {
                if (response.isExists()) {
                    System.out.println("response.toString() = " + response.toString());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });


    }


}
