package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 精确查询
 * term 查询， 可以用它处理数字（numbers）、布尔值（Booleans）、日期（dates）以及文本（text）
 *
 * @author: andy
 * @create: 2019/7/5 11:14
 * @description:
 */
public class TermSearch {
    private static RestHighLevelClient restHighLevelClient = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        searchTerm();
    }

    public static void searchTerm() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        // ConstantScoreQuery 可以非评分模式来执行term查询，所有评分均为1
        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders
                .constantScoreQuery(QueryBuilders.termQuery("salary", "1300"));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(constantScoreQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("search = " + search);
        long totalHits = search.getHits().getTotalHits();
        System.out.println("totalHits = " + totalHits);
    }
}
