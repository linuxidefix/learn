package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author: andy
 * @create: 2019/7/1 11:09
 * @description: ElasticSearch DeleteApi
 */
public class DeleteApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        delete();
    }

    /**
     * 同步删除
     *
     * @throws IOException
     */
    public static void delete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("andi_index", "_doc", "3");
        // 指定routing
//        deleteRequest.routing("4");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            // 该文档不存在时
        }
        String index = deleteResponse.getIndex();
        String type = deleteResponse.getType();
        String id = deleteResponse.getId();
        long version = deleteResponse.getVersion();
        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            // 删除成功分区小于总分区
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                // 删除失败处理逻辑
            }
        }
    }

    /**
     * 异步删除
     *
     * @throws IOException
     */
    public static void deleteAsync() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("accounuts", "person", "1");
        client.deleteAsync(deleteRequest, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                    // 该文档不存在时
                }
                String index = deleteResponse.getIndex();
                String type = deleteResponse.getType();
                String id = deleteResponse.getId();
                long version = deleteResponse.getVersion();
                ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    // 删除成功分区小于总分区
                }
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure :
                            shardInfo.getFailures()) {
                        String reason = failure.reason();
                        // 删除失败处理逻辑
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
