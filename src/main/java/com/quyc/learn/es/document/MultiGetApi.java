package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author: andy
 * @create: 2019/7/1 16:24
 * @description: ElasticSearch Multi-Get
 */
public class MultiGetApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
//        multiGet();
        multiGetVersion();

    }

    /**
     * 同时查询多个文档
     *
     * @throws IOException
     */
    public static void multiGet() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("index_not_exist", "doc", "1").fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
        request.add(new MultiGetRequest.Item("posts", "doc", "2"));
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
//        MultiGetItemResponse firstItem = response.getResponses()[1];
        for (MultiGetItemResponse itemResponse : response.getResponses()) {
//            assertNull(itemResponse.getFailure());
            // 查询失败
            if (itemResponse.isFailed()) {
                // 获取失败异常
                ElasticsearchException failure = (ElasticsearchException) itemResponse.getFailure().getFailure();
                System.out.println("failure.status() = " + failure.status());
                System.out.println("failure.toString() = " + failure.toString());
                continue;
            }
            GetResponse getResponse = itemResponse.getResponse();
            String index = itemResponse.getIndex();
            String type = itemResponse.getType();
            String id = itemResponse.getId();
            if (getResponse.isExists()) {
                long version = getResponse.getVersion();
                String sourceAsString = getResponse.getSourceAsString();
                System.out.println("sourceAsString = " + sourceAsString);
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                byte[] sourceAsBytes = getResponse.getSourceAsBytes();
            } else {
                // 不存在该文档

            }
        }
    }

    /**
     * 指定版本查询文档
     *
     * @throws IOException
     */
    public static void multiGetVersion() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        // 指定version时，若该version不存在则会获取失败
        request.add(new MultiGetRequest.Item("posts", "doc", "9")
                .version(3));
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        MultiGetItemResponse item = response.getResponses()[0];
        assertNull(item.getResponse());
        ElasticsearchException failure = (ElasticsearchException) item.getFailure().getFailure();
        // TODO status is broken! fix in a followup
        // assertEquals(RestStatus.CONFLICT, ee.status());
        System.out.println("failure = " + failure);
    }
}
