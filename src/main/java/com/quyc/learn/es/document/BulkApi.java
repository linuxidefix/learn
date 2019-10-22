package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @author: andy
 * @create: 2019/7/1 14:40
 * @description: ElasticSearch BulkApi
 */
public class BulkApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        bulk();
    }

    public static void bulk() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("posts", "doc", "5")
                .source(XContentType.JSON, "field", "foo").opType(DocWriteRequest.OpType.CREATE));
        bulkRequest.add(new IndexRequest("posts", "doc", "6")
                .source(XContentType.JSON, "field", "bar"));
        bulkRequest.add(new IndexRequest("posts", "doc", "7")
                .source(XContentType.JSON, "field", "baz"));
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 检查结果中是否有失败
        if (bulkResponse.hasFailures()) {
            // 遍历结果集
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                // 判断该结果是否失败
                if (bulkItemResponse.isFailed()) {
                    // 失败处理
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    System.out.println("failure.toString() = " + failure.toString());
                }
            }
        }
        // 遍历结果集，针对不同的结果类型进行不同的处理
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    // 创建索引结果处理
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    // 更新索引结果处理
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    // 删除索引结果处理
                default:
                    // 默认处理
            }
        }
    }

}
