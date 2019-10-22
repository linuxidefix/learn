package com.quyc.learn.es.document;

import com.alibaba.fastjson.JSON;
import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: andy
 * @create: 2019/7/1 11:16
 * @description: ElasticSearch UpdateApi
 */
public class UpdateApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
//        update();
        updateByQuery();
    }

    public static void update() throws IOException {
        UpdateRequest request = new UpdateRequest("andi_index", "_doc", "2");
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, String> son = new HashMap<>();
        jsonMap.put("text", "this is another answer");
        son.put("name", "answer");
        son.put("parent", "4");
        jsonMap.put("andi_index_field", son);
        request.doc(JSON.toJSONString(jsonMap), XContentType.JSON);
        // 更新之后拉取最新的文档
        request.fetchSource(true);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        String index = updateResponse.getIndex();
        String type = updateResponse.getType();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            // 文档第一次创建时
            System.out.println("文档第一次创建时");
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            // 文档被更新时
            System.out.println("文档被更新");
        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
            // 文档被删除时
            System.out.println("文档被删除时");
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
            // 更新操作不生效时
            System.out.println("更新操作不生效时");
        }

        // 获取更新之后的文档
        GetResult result = updateResponse.getGetResult();
        if (result.isExists()) {
            String sourceAsString = result.sourceAsString();
            System.out.println("sourceAsString = " + sourceAsString);
            Map<String, Object> sourceAsMap = result.sourceAsMap();
            byte[] sourceAsBytes = result.source();
        } else {

        }

        // 分区更新不全时的处理
        ReplicationResponse.ShardInfo shardInfo = updateResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
            }
        }

    }

    public static void updateByQuery() throws IOException {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("person");
        updateByQueryRequest.setQuery(QueryBuilders.termQuery("firstName.keyword","andy008"));
        Map<String, Object> map = new HashMap<>(2);
        map.put("firstName", "andy100");
        map.put("salary", 1100);
        updateByQueryRequest.setScript(new Script(ScriptType.INLINE, "painless",
                "ctx._source.firstName=params.firstName;ctx._source.salary=params.salary", map));
        BulkByScrollResponse response = client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        System.out.println("response = " + response);
    }


}
