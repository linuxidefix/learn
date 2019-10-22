package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 同时执行多个查询，查询之间无关联，可得到多个结果，执行顺序与添加顺序一致
 *
 * @author: andy
 * @create: 2019/7/3 17:45
 * @description: ElasticSearch MultiSearch Api
 */
public class MultiSearch {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        searchMulti();
    }

    public static void searchMulti() throws IOException {
        // 创建
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("gender", "female"));
        firstSearchRequest.source(searchSourceBuilder);
        // 添加查询
        multiSearchRequest.add(firstSearchRequest);
        SearchRequest secondSearchRequest = new SearchRequest("person");
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("salary").gt("1300"));
        secondSearchRequest.source(searchSourceBuilder);
        multiSearchRequest.add(secondSearchRequest);
        // 执行查询
        MultiSearchResponse msearch = client.msearch(multiSearchRequest, RequestOptions.DEFAULT);
        System.out.println("msearch = " + msearch);
        MultiSearchResponse.Item firstResponse = msearch.getResponses()[0];
        SearchResponse firstResponseResponse = firstResponse.getResponse();
        System.out.println("firstResponseResponse = " + firstResponseResponse);
        MultiSearchResponse.Item secondResponse = msearch.getResponses()[1];
        SearchResponse secondResponseResponse = secondResponse.getResponse();
        System.out.println("secondResponseResponse = " + secondResponseResponse);
    }

}
