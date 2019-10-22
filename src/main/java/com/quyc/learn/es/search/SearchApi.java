package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: andy
 * @create: 2019/7/1 19:35
 * @description: ElasticSearch Search Api
 */
public class SearchApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
//        search();
//        searchQuery();
//        searchQueryHighlight();
//        searchAggregation();
        searchAggregationCardinality();
//        searchQueryHighlight();
//        searchParentChild();

    }

    /**
     * 简单搜索
     *
     * @throws IOException
     */
    public static void search() throws IOException {
        // 创建一个空的SearchRequest，则对所有index进行搜索，传入index可对该index进行搜索
        SearchRequest searchRequest = new SearchRequest("posts");
        // 可通过 SearchSourceBuilder 设置任意查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // A query that matches on all documents.
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // Controls how to deal with unavailable concrete indices (closed or missing)
        // 忽略不存在的indices，允许出现没有indices
        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
        // 设置搜索偏好，默认随机选择shard进行搜索，可设置：_local,_primary
        // or a custom value, which guarantees that the same order will be used across different requests.
        searchRequest.preference("_local");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
    }

    /**
     * 通过 SearchSourceBuilder 条件化搜索
     *
     * @throws IOException the io exception
     */
    public static void searchQuery() throws IOException {
        // 创建一个空的SearchRequest，则对所有index进行搜索，传入index可对该index进行搜索
        SearchRequest searchRequest = new SearchRequest("posts");
        // 可通过 SearchSourceBuilder 设置任意查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 可设置任意查询类型
//        searchSourceBuilder.query(QueryBuilders.termQuery("message","trying out Elasticsearch"));
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("message", "trying");
        // 模糊搜索
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        // todo 还需研究 设置模糊匹配的前缀长度
        matchQueryBuilder.prefixLength(3);
        // todo 还需研究 最大匹配词条数
        matchQueryBuilder.maxExpansions(10);
        // 使用QueryBuilders实现同样的设置
        QueryBuilders.matchQuery("title", "Current status and upcoming")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3)
                .maxExpansions(10);
        searchSourceBuilder.query(matchQueryBuilder);
        // 包含哪些field
        String[] includeFields = {"user", "title", "*date"};
        // 不包含
        String[] excludeFields = {"message"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);
        // 根据评分倒序排列
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.ASC));
        // todo 还需研究 根据user这个字段进行正序排列
        searchSourceBuilder.sort(new FieldSortBuilder("user").order(SortOrder.ASC));
        // 设置偏移量
        searchSourceBuilder.from(0);
        // 设置步长
//        searchSourceBuilder.size(2);
        // 设置超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
    }

    /**
     * 高亮显示结果
     *
     * @throws IOException the io exception
     */
    public static void searchQueryHighlight() throws IOException {
        // 创建一个空的SearchRequest，则对所有index进行搜索，传入index可对该index进行搜索
        SearchRequest searchRequest = new SearchRequest("posts");
        // 可通过 SearchSourceBuilder 设置任意查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 使用and逻辑运算符
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "In which order").operator(Operator.AND));
        // 初始化 HighlightBuilder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 创建 HighlightBuilder.Field，指定高亮属性名，该属性名需要在 query 语句搜索条件内
        HighlightBuilder.Field highLightTitle = new HighlightBuilder.Field("title");
        // 指定高亮形式 {@code unified}, {@code plain} and {@code fvj}，默认unified
        highLightTitle.highlighterType("plain");
        // 添加高亮field，该方法调用多次可添加多个高亮属性
        highlightBuilder.field(highLightTitle);
        HighlightBuilder.Field highLightUser = new HighlightBuilder.Field("user");
        highlightBuilder.field(highLightUser);
        // 添加高亮配置
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("title");
            Text[] fragments = highlight.fragments();
            String fragmentString = fragments[0].string();
            System.out.println("fragmentString = " + fragmentString);
        }
    }

    /**
     * 聚合函数
     *
     * @throws IOException
     */
    public static void searchAggregation() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置聚合条件 即根据性别分组，此处注意使用 gender.keyword
        TermsAggregationBuilder byGender = AggregationBuilders.terms("by_gender").field("gender.keyword");
        // 设置聚合函数
        byGender.subAggregation(AggregationBuilders.avg("avg_salary").field("salary"));
        searchSourceBuilder.aggregation(byGender);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
        Aggregations aggregations = searchResponse.getAggregations();
        // 解析聚合结果
        Terms byGenderAggregation = aggregations.get("by_gender");
        Terms.Bucket female = byGenderAggregation.getBucketByKey("female");
        Avg avgSalary = female.getAggregations().get("avg_salary");
        double value = avgSalary.getValue();
        System.out.println("value = " + value);
    }

    /**
     * 去重聚合函数
     *
     * @throws IOException
     */
    public static void searchAggregationCardinality() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CardinalityAggregationBuilder builder = new CardinalityAggregationBuilder("distinct_salary", ValueType.STRING);
        builder.field("gender.keyword");
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder.aggregation(builder));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
        Aggregations aggregations = searchResponse.getAggregations();
        Cardinality cardinality = aggregations.get("distinct_salary");
        long value = cardinality.getValue();
        System.out.println("value = " + value);
    }

    /**
     * 父子关系查询
     *
     * @throws IOException
     */
    public static void searchParentChild() throws IOException {
        SearchRequest searchRequest = new SearchRequest("andi_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        HasParentQueryBuilder hasParentQueryBuilder = new HasParentQueryBuilder("question", QueryBuilders.matchQuery("text", "This"), true);
        searchSourceBuilder.query(hasParentQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("search = " + search);
    }

}
