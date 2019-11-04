package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author: andy
 * @create: 2019/10/25 20:53
 * @description:
 */
public class CountSearch {

    private static RestHighLevelClient restHighLevelClient = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        count();
    }

    private static void count() throws IOException {
        CountRequest countRequest = new CountRequest("buyer_portrait_calculate_alias");
        countRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        CountResponse count = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
        System.out.println("count = " + count);
    }

}
