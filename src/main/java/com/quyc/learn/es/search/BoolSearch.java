package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import com.quyc.learn.lambda.streams.Person;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * bool 组合查询
 * 发生	描述
 * must 如果有多个条件，这些条件都必须满足  and 与
 * 该条款（查询）必须出现在匹配的文件，并将有助于得分。
 * filter 过滤，不参与打分
 * 子句（查询）必须出现在匹配的文档中。然而不像 must查询的分数将被忽略。Filter子句在过滤器上下文中执行，这意味着评分被忽略，子句被考虑用于高速缓存。
 * should 如果有多个条件，满足一个或多个即可 or 或
 * 子句（查询）应该出现在匹配的文档中。如果 bool查询位于查询上下文中并且具有mustor filter子句，则bool即使没有should查询匹配，文档也将匹配该查询 。在这种情况下，这些条款仅用于影响分数。如果bool查询是过滤器上下文 或者两者都不存在，must或者filter至少有一个should查询必须与文档相匹配才能与bool查询匹配。这种行为可以通过设置minimum_should_match参数来显式控制 。
 * must_not 和must相反，必须都不满足条件才可以匹配到 ！非
 * 子句（查询）不能出现在匹配的文档中。子句在过滤器上下文中执行，意味着评分被忽略，子句被考虑用于高速缓存。因为计分被忽略，0所有文件的分数被返回。
 *
 * @author: andy
 * @create: 2019/7/5 13:44
 * @description: ElasticSearch Bool Api
 */
public class BoolSearch {

    private static RestHighLevelClient restHighLevelClient = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        searchBool();
    }

    public static void searchBool() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                // 性别是female
                .must(QueryBuilders.termQuery("gender", "female"))
                // firstName 为Jayden
                .filter(QueryBuilders.termQuery("firstName.keyword", "Jayden"))
                // 不包含年龄在 20-26 之间的
                .mustNot(QueryBuilders.rangeQuery("age").gte(20).lte(26))
                // 薪资是1200或1700
//                .should(QueryBuilders.termQuery("salary", 1200))
//                .should(QueryBuilders.termQuery("salary", 1700))
                .should(QueryBuilders.termsQuery("salary", Arrays.asList(1200, 1700)))
                // should条件必须满足一个
                .minimumShouldMatch(1);
        System.out.println("boolQueryBuilder = " + boolQueryBuilder);
        searchRequest.source(new SearchSourceBuilder().query(boolQueryBuilder));
        System.out.println("searchRequest = " + searchRequest);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("search = " + search);
        List<Person> persons = EsClientUtil.getSearchResult(search, Person.class);
        persons.forEach(System.out::println);
    }

}
