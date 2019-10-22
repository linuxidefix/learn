package com.quyc.learn.es.demo;

import com.quyc.learn.es.EsClientUtil;
import com.quyc.learn.lambda.streams.Person;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

/**
 * 搜索数据的解析
 *
 * @author: andy
 * @create: 2019/7/1 19:35
 * @description: ElasticSearch Search Api
 */
public class SearchForObj {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        search();
    }

    /**
     * 简单搜索
     *
     * @throws IOException
     */
    public static void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
        List<Person> personList = EsClientUtil.getSearchResult(searchResponse, Person.class);
        if (personList == null) {
            return;
        }
        personList.forEach(System.out::println);
    }


}
