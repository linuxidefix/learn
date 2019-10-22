package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author: andy
 * @create: 2019/7/1 10:41
 * @description: ElasticSearch ExistApi
 */
public class ExistApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        exist();
    }

    public static void exist() throws IOException {
        GetRequest getRequest = new GetRequest("kibana_sample_data_ecommerce", "_doc", "h-tsp2sBkLs8z_-vaq8x");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("exists = " + exists);
        client.existsAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean aBoolean) {
                System.out.println("aBoolean = " + aBoolean);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}
