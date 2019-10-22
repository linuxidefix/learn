package com.quyc.learn.es.searchinaction;

import com.google.common.collect.Lists;
import com.quyc.learn.es.EsClientUtil;
import com.quyc.learn.es.searchinaction.entity.*;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: andy
 * @create: 2019/7/8 19:22
 * @description:
 */
public class SearchInAction {

    private static RestHighLevelClient restHighLevelClient = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException, ParseException {
        BuyerPortraitQuery query = new BuyerPortraitQuery();
        query.setShopId("SHP005405234151200242AC110002640");
//        query.setBuyerId("大好青年梁二蛋");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2019-04-02 11:56:21");
        Date minDate = DateUtils.addDays(date, -1);
        Date maxDate = DateUtils.addDays(date, 1);
//        query.setStartPayedTime(minDate);
//        query.setEndPayedTime(maxDate);
        query.setMatchRule(1);
//        query.setSex(1);
        query.setSexs(Lists.newArrayList(2));
//        countSingleLabelBuyerPortrait(query);
//        test(query);
        scroll();
    }

    public static void scroll() throws IOException {
        // 设置时效
        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(5));
        SearchRequest searchRequest = new SearchRequest("person");
//        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("distinct_salary").field("salary");
//        aggregationBuilder.subAggregation(AggregationBuilders.avg("avg_age").field("age"));
        CardinalityAggregationBuilder aggregationBuilder = AggregationBuilders.cardinality("salary").field("salary");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregationBuilder);
//        searchSourceBuilder.query(QueryBuilders.termQuery("shop_id.keyword", "SHP685588834311210242AC110002956"));
//        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
//        searchRequest.scroll(scroll);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);
        // 获取scrollId，后续搜索根据这个id进行搜索
        String scrollId = searchResponse.getScrollId();
        System.out.println("scrollId = " + scrollId);
        SearchHit[] hits = searchResponse.getHits().getHits();
//        // 循环搜索，直到搜索结束
//        while (hits != null && hits.length > 0) {
//            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//            scrollRequest.scroll(scroll);
//            SearchResponse searchScrollResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
//            System.out.println("searchScrollResponse = " + searchScrollResponse);
//            hits = searchScrollResponse.getHits().getHits();
//            System.out.println("hits = " + hits);
//        }
//        // 清理scroll上下文，若不及时清理会在时效到期自动清除
//        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//        clearScrollRequest.addScrollId(scrollId);
//        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//        boolean succeeded = clearScrollResponse.isSucceeded();
//        System.out.println("succeeded = " + succeeded);
    }

    /**
     * 构建总查询条件
     *
     * @param query
     * @param builder
     */
    private void buildQuery(BuyerPortraitQuery query, BoolQueryBuilder builder) {
        builder.must(QueryBuilders.termsQuery("shop_id.keyword", query.getShopId()));
        if (query.getMatchRule() != null && query.getMatchRule() == 1) {
            if (query.getStartPayedTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_payed").gte(query.getStartPayedTime()));
            }
            if (query.getEndPayedTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_payed").lte(query.getEndPayedTime()));
            }
            if (query.getStartConsulTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_consultation").gte(query.getStartConsulTime()));
            }
            if (query.getEndConsulTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_consultation").lte(query.getEndConsulTime()));
            }
            if (!CollectionUtils.isEmpty(query.getSexs())) {
                builder.must(QueryBuilders.termsQuery("sex", query.getSexs()));
            }
            if (!CollectionUtils.isEmpty(query.getProvinces())) {
                builder.must(QueryBuilders.termsQuery("province.keyword", query.getProvinces()));
            }
            if (!CollectionUtils.isEmpty(query.getCitys())) {
                builder.must(QueryBuilders.termsQuery("city.keyword", query.getCitys()));
            }
            if (!CollectionUtils.isEmpty(query.getAreas())) {
                builder.must(QueryBuilders.termsQuery("area.keyword", query.getAreas()));
            }
            if (query.getMinOrderPayed() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed").gte(query.getMinOrderPayed()));
            }
            if (query.getMaxOrderPayed() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed").lte(query.getMaxOrderPayed()));
            }
            if (query.getMinOrderPayed30() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_30").gte(query.getMinOrderPayed30()));
            }
            if (query.getMaxOrderPayed30() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_30").lte(query.getMaxOrderPayed30()));
            }
            if (query.getMinOrderPayed60() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_60").gte(query.getMinOrderPayed60()));
            }
            if (query.getMaxOrderPayed60() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_60").lte(query.getMaxOrderPayed60()));
            }
            if (query.getMinOrderPayed90() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_90").gte(query.getMinOrderPayed90()));
            }
            if (query.getMaxOrderPayed90() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_90").lte(query.getMaxOrderPayed90()));
            }
            if (query.getMinOrderPayed180() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_180").gte(query.getMinOrderPayed180()));
            }
            if (query.getMaxOrderPayed180() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed_180").lte(query.getMaxOrderPayed180()));
            }
            if (query.getMinOrderCount() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count").gte(query.getMinOrderCount()));
            }
            if (query.getMaxOrderCount() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count").lte(query.getMaxOrderCount()));
            }
            if (query.getMinOrderCount30() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_30").gte(query.getMinOrderCount30()));
            }
            if (query.getMaxOrderCount30() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_30").lte(query.getMaxOrderCount30()));
            }
            if (query.getMinOrderCount60() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_60").gte(query.getMinOrderCount60()));
            }
            if (query.getMaxOrderCount60() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_60").lte(query.getMaxOrderCount60()));
            }
            if (query.getMinOrderCount90() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_90").gte(query.getMinOrderCount90()));
            }
            if (query.getMaxOrderCount90() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_90").lte(query.getMaxOrderCount90()));
            }
            if (query.getMinOrderCount180() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_180").gte(query.getMinOrderCount180()));
            }
            if (query.getMaxOrderCount180() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count_180").lte(query.getMaxOrderCount180()));
            }
            if (query.getMinAverageProduct() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product").gte(query.getMinAverageProduct()));
            }
            if (query.getMaxAverageProduct() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product").lte(query.getMaxAverageProduct()));
            }
            if (query.getMinAverageProduct30() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_30").gte(query.getMinAverageProduct30()));
            }
            if (query.getMaxAverageProduct30() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_30").lte(query.getMaxAverageProduct30()));
            }
            if (query.getMinAverageProduct60() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_60").gte(query.getMinAverageProduct60()));
            }
            if (query.getMaxAverageProduct60() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_60").lte(query.getMaxAverageProduct60()));
            }
            if (query.getMinAverageProduct90() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_90").gte(query.getMinAverageProduct90()));
            }
            if (query.getMaxAverageProduct90() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_90").lte(query.getMaxAverageProduct90()));
            }
            if (query.getMinAverageProduct180() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_180").gte(query.getMinAverageProduct180()));
            }
            if (query.getMaxAverageProduct180() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product_180").lte(query.getMaxAverageProduct180()));
            }
            if (query.getMinProductCount() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count").gte(query.getMinProductCount()));
            }
            if (query.getMaxProductCount() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count").lte(query.getMaxProductCount()));
            }
            if (query.getMinProductCount30() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_30").gte(query.getMinProductCount30()));
            }
            if (query.getMaxProductCount30() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_30").lte(query.getMaxProductCount30()));
            }
            if (query.getMinProductCount60() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_60").gte(query.getMinProductCount60()));
            }
            if (query.getMaxProductCount60() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_60").lte(query.getMaxProductCount60()));
            }
            if (query.getMinProductCount90() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_90").gte(query.getMinProductCount90()));
            }
            if (query.getMaxProductCount90() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_90").lte(query.getMaxProductCount90()));
            }
            if (query.getMinProductCount180() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_180").gte(query.getMinProductCount180()));
            }
            if (query.getMaxProductCount180() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count_180").lte(query.getMaxProductCount180()));
            }
            if (query.getOrderConsultTag() != null && query.getNonOrderConsultTag() == null) {
                OrderConsultEnum.buildOrderConsultQuery(query);
                if (null != query.getConsult7() && query.getConsult7() > 0L) {
                    builder.must(QueryBuilders.termQuery("consult_7", query.getConsult7()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getConsult15() && query.getConsult15() > 0L) {
                    builder.must(QueryBuilders.termQuery("consult_15", query.getConsult15()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getConsult30() && query.getConsult30() > 0L) {
                    builder.must(QueryBuilders.termQuery("consult_30", query.getConsult30()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getConsultAll() && query.getConsultAll() > 0L) {
                    builder.must(QueryBuilders.termQuery("consult_all", query.getConsultAll()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getUnOrder7() && query.getUnOrder7() > 0L) {
                    builder.must(QueryBuilders.termQuery("un_order_7", query.getUnOrder7()));
                }
                if (null != query.getUnOrder15() && query.getUnOrder15() > 0L) {
                    builder.must(QueryBuilders.termQuery("un_order_15", query.getUnOrder15()));
                }
                if (null != query.getUnOrder30() && query.getUnOrder30() > 0L) {
                    builder.must(QueryBuilders.termQuery("un_order_30", query.getUnOrder30()));
                }
                if (null != query.getUnOrderAll() && query.getUnOrderAll() > 0L) {
                    builder.must(QueryBuilders.termQuery("un_order_All", query.getUnOrderAll()));
                }
                if (null != query.getPayed30() && query.getPayed30() > 0L) {
                    builder.must(QueryBuilders.termQuery("payed_30", query.getPayed30()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayed60() && query.getPayed60() > 0L) {
                    builder.must(QueryBuilders.termQuery("payed_60", query.getPayed60()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayed90() && query.getPayed90() > 0L) {
                    builder.must(QueryBuilders.termQuery("payed_90", query.getPayed90()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayed180() && query.getPayed180() > 0L) {
                    builder.must(QueryBuilders.termQuery("payed_180", query.getPayed180()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayedAll() && query.getPayedAll() > 0L) {
                    builder.must(QueryBuilders.termQuery("payed_all", query.getPayedAll()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getHasMessage30() && query.getHasMessage30() > 0L) {
                    builder.must(QueryBuilders.termQuery("has_message_30", query.getHasMessage30()));
                }
                if (null != query.getNoMessage28() && query.getNoMessage28() > 0L) {
                    builder.must(QueryBuilders.termQuery("no_message_28", query.getNoMessage28()));
                }
                if (null != query.getNoOrder28() && query.getNoOrder28() > 0L) {
                    builder.must(QueryBuilders.termQuery("no_order_28", query.getNoOrder28()));
                }
            }
            if (query.getOrderConsultTag() == null && query.getNonOrderConsultTag() != null) {
                builder.must(QueryBuilders.termQuery("un_order_30", 0L));
            }
            if (query.getOrderConsultTag() != null && query.getNonOrderConsultTag() != null) {
                BoolQueryBuilder builderOrderConsultTag = QueryBuilders.boolQuery();
                OrderConsultEnum.buildOrderConsultQuery(query);
                if (null != query.getConsult7() && query.getConsult7() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("consult_7", query.getConsult7()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getConsult15() && query.getConsult15() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("consult_15", query.getConsult15()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getConsult30() && query.getConsult30() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("consult_30", query.getConsult30()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getConsultAll() && query.getConsultAll() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("consult_all", query.getConsultAll()));
                    actionProductProcess(builder, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                }
                if (null != query.getUnOrder7() && query.getUnOrder7() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("un_order_7", query.getUnOrder7()));
                }
                if (null != query.getUnOrder15() && query.getUnOrder15() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("un_order_15", query.getUnOrder15()));
                }
                if (null != query.getUnOrder30() && query.getUnOrder30() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("un_order_30", query.getUnOrder30()));
                }
                if (null != query.getUnOrderAll() && query.getUnOrderAll() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("un_order_All", query.getUnOrderAll()));
                }
                if (null != query.getPayed30() && query.getPayed30() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("payed_30", query.getPayed30()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayed60() && query.getPayed60() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("payed_60", query.getPayed60()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayed90() && query.getPayed90() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("payed_90", query.getPayed90()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayed180() && query.getPayed180() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("payed_180", query.getPayed180()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getPayedAll() && query.getPayedAll() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("payed_all", query.getPayedAll()));
                    actionProductProcess(builder, query, OrderPayedRuleCondition.CONDITION_CODE);
                }
                if (null != query.getHasMessage30() && query.getHasMessage30() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("has_message_30", query.getHasMessage30()));
                }
                if (null != query.getNoMessage28() && query.getNoMessage28() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("no_message_28", query.getNoMessage28()));
                }
                if (null != query.getNoOrder28() && query.getNoOrder28() > 0L) {
                    builderOrderConsultTag.must(QueryBuilders.termQuery("no_order_28", query.getNoOrder28()));
                }

                BoolQueryBuilder builderOrderConsultTagAndNonOrderConsultTag = QueryBuilders.boolQuery();
                builderOrderConsultTagAndNonOrderConsultTag.should(QueryBuilders.termQuery("un_order_30", 0L));
                builderOrderConsultTagAndNonOrderConsultTag.should(builderOrderConsultTag);
                builderOrderConsultTagAndNonOrderConsultTag.minimumShouldMatch(1);

                builder.must(builderOrderConsultTagAndNonOrderConsultTag);
            }
        } else {
            if (query.getStartPayedTime() != null && query.getEndPayedTime() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("gmt_last_payed")
                        .gte(query.getStartPayedTime())
                        .lte(query.getEndPayedTime()));
            }
            if (query.getStartPayedTime() != null && query.getEndPayedTime() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("gmt_last_payed").gte(query.getStartPayedTime()));
            }
            if (query.getStartPayedTime() == null && query.getEndPayedTime() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("gmt_last_payed").lte(query.getEndPayedTime()));
            }
            if (query.getStartConsulTime() != null && query.getEndConsulTime() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("gmt_last_consultation")
                        .gte(query.getStartConsulTime())
                        .lte(query.getEndConsulTime()));
            }
            if (query.getStartConsulTime() != null && query.getEndConsulTime() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("gmt_last_consultation").gte(query.getStartConsulTime()));
            }
            if (query.getStartConsulTime() == null && query.getEndConsulTime() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("gmt_last_consultation").lte(query.getEndConsulTime()));
            }
            if (!CollectionUtils.isEmpty(query.getSexs())) {
                query.setHasShould(true);
                builder.should(QueryBuilders.termsQuery("sex", query.getSexs()));
            }
            if (!CollectionUtils.isEmpty(query.getProvinces())) {
                query.setHasShould(true);
                builder.should(QueryBuilders.termsQuery("province.keyword", query.getProvinces()));
            }
            if (!CollectionUtils.isEmpty(query.getCitys())) {
                query.setHasShould(true);
                builder.should(QueryBuilders.termsQuery("city.keyword", query.getCitys()));
            }
            if (!CollectionUtils.isEmpty(query.getAreas())) {
                query.setHasShould(true);
                builder.should(QueryBuilders.termsQuery("area.keyword", query.getAreas()));
            }
            if (query.getMinOrderPayed() != null && query.getMaxOrderPayed() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed")
                        .gte(query.getMinOrderPayed())
                        .lte(query.getMaxOrderPayed()));
            }
            if (query.getMinOrderPayed() != null && query.getMaxOrderPayed() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed").gte(query.getMinOrderPayed()));
            }
            if (query.getMinOrderPayed() == null && query.getMaxOrderPayed() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed").lte(query.getMaxOrderPayed()));
            }
            if (query.getMinOrderPayed30() != null && query.getMaxOrderPayed30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_30")
                        .gte(query.getMinOrderPayed30())
                        .lte(query.getMaxOrderPayed30()));
            }
            if (query.getMinOrderPayed30() != null && query.getMaxOrderPayed30() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_30").gte(query.getMinOrderPayed30()));
            }
            if (query.getMinOrderPayed30() == null && query.getMaxOrderPayed30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_30").lte(query.getMaxOrderPayed30()));
            }
            if (query.getMinOrderPayed60() != null && query.getMaxOrderPayed60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_60")
                        .gte(query.getMinOrderPayed60())
                        .lte(query.getMaxOrderPayed60()));
            }
            if (query.getMinOrderPayed60() != null && query.getMaxOrderPayed60() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_60").gte(query.getMinOrderPayed60()));
            }
            if (query.getMinOrderPayed60() == null && query.getMaxOrderPayed60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_60").lte(query.getMaxOrderPayed60()));
            }
            if (query.getMinOrderPayed90() != null && query.getMaxOrderPayed90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_90")
                        .gte(query.getMinOrderPayed90()).lte(query.getMaxOrderPayed90()));
            }
            if (query.getMinOrderPayed90() != null && query.getMaxOrderPayed90() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_90").gte(query.getMinOrderPayed90()));
            }
            if (query.getMinOrderPayed90() == null && query.getMaxOrderPayed90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_90").lte(query.getMaxOrderPayed90()));
            }
            if (query.getMinOrderPayed180() != null && query.getMaxOrderPayed180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_180_").gte(query.getMinOrderPayed180())
                        .lte(query.getMaxOrderPayed180()));
            }
            if (query.getMinOrderPayed180() != null && query.getMaxOrderPayed180() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_180").gte(query.getMinOrderPayed180()));
            }
            if (query.getMinOrderPayed180() == null && query.getMaxOrderPayed180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_payed_180").lte(query.getMaxOrderPayed180()));
            }
            if (query.getMaxOrderCount() != null && query.getMinOrderCount() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count")
                        .gte(query.getMinOrderCount())
                        .lte(query.getMaxOrderCount()));
            }
            if (query.getMaxOrderCount() != null && query.getMinOrderCount() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count").lte(query.getMaxOrderCount()));
            }
            if (query.getMaxOrderCount() == null && query.getMinOrderCount() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count").gte(query.getMinOrderCount()));
            }
            if (query.getMaxOrderCount30() != null && query.getMinOrderCount30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_30")
                        .gte(query.getMinOrderCount30())
                        .lte(query.getMaxOrderCount30()));
            }
            if (query.getMaxOrderCount30() != null && query.getMinOrderCount30() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_30").lte(query.getMaxOrderCount30()));
            }
            if (query.getMaxOrderCount30() == null && query.getMinOrderCount30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_30").gte(query.getMinOrderCount30()));
            }
            if (query.getMaxOrderCount60() != null && query.getMinOrderCount60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_60")
                        .gte(query.getMinOrderCount60())
                        .lte(query.getMaxOrderCount60()));
            }
            if (query.getMaxOrderCount60() != null && query.getMinOrderCount60() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_60").lte(query.getMaxOrderCount60()));
            }
            if (query.getMaxOrderCount60() == null && query.getMinOrderCount60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_60").gte(query.getMinOrderCount60()));
            }
            if (query.getMaxOrderCount90() != null && query.getMinOrderCount90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_90")
                        .gte(query.getMinOrderCount90())
                        .lte(query.getMaxOrderCount90()));
            }
            if (query.getMaxOrderCount90() != null && query.getMinOrderCount90() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_90").lte(query.getMaxOrderCount90()));
            }
            if (query.getMaxOrderCount90() == null && query.getMinOrderCount90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_90").gte(query.getMinOrderCount90()));
            }
            if (query.getMaxOrderCount180() != null && query.getMinOrderCount180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_180")
                        .gte(query.getMinOrderCount180())
                        .lte(query.getMaxOrderCount180()));
            }
            if (query.getMaxOrderCount180() != null && query.getMinOrderCount180() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_180").lte(query.getMaxOrderCount180()));
            }
            if (query.getMaxOrderCount180() == null && query.getMinOrderCount180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("order_count_180").gte(query.getMinOrderCount180()));
            }
            if (query.getMaxAverageProduct() != null && query.getMinAverageProduct() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product")
                        .gte(query.getMinAverageProduct())
                        .lte(query.getMaxAverageProduct()));
            }
            if (query.getMaxAverageProduct() != null && query.getMinAverageProduct() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product").lte(query.getMaxAverageProduct()));
            }
            if (query.getMaxAverageProduct() == null && query.getMinAverageProduct() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product").gte(query.getMinAverageProduct()));
            }
            if (query.getMaxAverageProduct30() != null && query.getMinAverageProduct30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_30")
                        .gte(query.getMinAverageProduct30())
                        .lte(query.getMaxAverageProduct30()));
            }
            if (query.getMaxAverageProduct30() != null && query.getMinAverageProduct30() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_30").lte(query.getMaxAverageProduct30()));
            }
            if (query.getMaxAverageProduct30() == null && query.getMinAverageProduct30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_30").gte(query.getMinAverageProduct30()));
            }
            if (query.getMaxAverageProduct60() != null && query.getMinAverageProduct60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_60")
                        .gte(query.getMinAverageProduct60())
                        .lte(query.getMaxAverageProduct60()));
            }
            if (query.getMaxAverageProduct60() != null && query.getMinAverageProduct60() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_60").lte(query.getMaxAverageProduct60()));
            }
            if (query.getMaxAverageProduct60() == null && query.getMinAverageProduct60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_60").gte(query.getMinAverageProduct60()));
            }
            if (query.getMaxAverageProduct90() != null && query.getMinAverageProduct90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_90")
                        .gte(query.getMinAverageProduct90())
                        .lte(query.getMaxAverageProduct90()));
            }
            if (query.getMaxAverageProduct90() != null && query.getMinAverageProduct90() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_90").lte(query.getMaxAverageProduct90()));
            }
            if (query.getMaxAverageProduct90() == null && query.getMinAverageProduct90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_90").gte(query.getMinAverageProduct90()));
            }
            if (query.getMaxAverageProduct180() != null && query.getMinAverageProduct180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_180")
                        .gte(query.getMinAverageProduct180())
                        .lte(query.getMaxAverageProduct180()));
            }
            if (query.getMaxAverageProduct180() != null && query.getMinAverageProduct180() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_180").lte(query.getMaxAverageProduct180()));
            }
            if (query.getMaxAverageProduct180() == null && query.getMinAverageProduct180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("average_product_180").gte(query.getMinAverageProduct180()));
            }
            if (query.getMaxProductCount() != null && query.getMinProductCount() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count")
                        .gte(query.getMinProductCount())
                        .lte(query.getMaxProductCount()));
            }
            if (query.getMaxProductCount() != null && query.getMinProductCount() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count").lte(query.getMaxProductCount()));
            }
            if (query.getMaxProductCount() == null && query.getMinProductCount() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count").gte(query.getMinProductCount()));
            }
            if (query.getMaxProductCount30() != null && query.getMinProductCount30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_30")
                        .gte(query.getMinProductCount30())
                        .lte(query.getMaxProductCount30()));
            }
            if (query.getMaxProductCount30() != null && query.getMinProductCount30() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_30").lte(query.getMaxProductCount30()));
            }
            if (query.getMaxProductCount30() == null && query.getMinProductCount30() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_30").gte(query.getMinProductCount30()));
            }
            if (query.getMaxProductCount60() != null && query.getMinProductCount60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_60")
                        .gte(query.getMinProductCount60())
                        .lte(query.getMaxProductCount60()));
            }
            if (query.getMaxProductCount60() != null && query.getMinProductCount60() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_60").lte(query.getMaxProductCount60()));
            }
            if (query.getMaxProductCount60() == null && query.getMinProductCount60() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_60").gte(query.getMinProductCount60()));
            }
            if (query.getMaxProductCount90() != null && query.getMinProductCount90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_90")
                        .gte(query.getMinProductCount90())
                        .lte(query.getMaxProductCount90()));
            }
            if (query.getMaxProductCount90() != null && query.getMinProductCount90() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_90").lte(query.getMaxProductCount90()));
            }
            if (query.getMaxProductCount90() == null && query.getMinProductCount90() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_90").gte(query.getMinProductCount90()));
            }
            if (query.getMaxProductCount180() != null && query.getMinProductCount180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_180")
                        .gte(query.getMinProductCount180())
                        .lte(query.getMaxProductCount180()));
            }
            if (query.getMaxProductCount180() != null && query.getMinProductCount180() == null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_180").lte(query.getMaxProductCount180()));
            }
            if (query.getMaxProductCount180() == null && query.getMinProductCount180() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.rangeQuery("product_count_180").gte(query.getMinProductCount180()));
            }
            if (query.getOrderConsultTag() != null) {

                OrderConsultEnum.buildOrderConsultQuery(query);
                if (null != query.getConsult7() && query.getConsult7() > 0L &&
                        null != query.getUnOrder7() && query.getUnOrder7() > 0L) {
                    BoolQueryBuilder consultUnOrderTag = QueryBuilders.boolQuery();
                    consultUnOrderTag.must(QueryBuilders.termQuery("un_order_7", query.getUnOrder7()));
                    consultUnOrderTag.must(QueryBuilders.termQuery("consult_7", query.getConsult7()));
                    actionProductProcess(consultUnOrderTag, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(consultUnOrderTag);
                }

                if (null != query.getConsult15() && query.getConsult15() > 0L &&
                        null != query.getUnOrder15() && query.getUnOrder15() > 0L) {
                    BoolQueryBuilder consultUnOrderTag = QueryBuilders.boolQuery();
                    consultUnOrderTag.must(QueryBuilders.termQuery("un_order_15", query.getUnOrder15()));
                    consultUnOrderTag.must(QueryBuilders.termQuery("consult_15", query.getConsult15()));
                    actionProductProcess(consultUnOrderTag, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(consultUnOrderTag);
                }

                if (null != query.getConsult30() && query.getConsult30() > 0L &&
                        null != query.getUnOrder30() && query.getUnOrder30() > 0L) {
                    BoolQueryBuilder consultUnOrderTag = QueryBuilders.boolQuery();
                    consultUnOrderTag.must(QueryBuilders.termQuery("un_order_30", query.getUnOrder30()));
                    consultUnOrderTag.must(QueryBuilders.termQuery("consult_30", query.getConsult30()));
                    actionProductProcess(consultUnOrderTag, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(consultUnOrderTag);
                }

                if (null != query.getConsultAll() && query.getConsultAll() > 0L &&
                        null != query.getUnOrderAll() && query.getUnOrderAll() > 0L) {
                    BoolQueryBuilder consultUnOrderTag = QueryBuilders.boolQuery();
                    consultUnOrderTag.must(QueryBuilders.termQuery("un_order_all", query.getUnOrderAll()));
                    consultUnOrderTag.must(QueryBuilders.termQuery("consult_all", query.getConsultAll()));
                    actionProductProcess(consultUnOrderTag, query, ConsultNoOrderRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(consultUnOrderTag);
                }

                if (null != query.getPayed30() && query.getPayed30() > 0L) {
                    BoolQueryBuilder payedTag = QueryBuilders.boolQuery();
                    payedTag.must(QueryBuilders.termQuery("payed_30", query.getPayed30()));
                    actionProductProcess(payedTag, query, OrderPayedRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(payedTag);
                }
                if (null != query.getPayed60() && query.getPayed60() > 0L) {
                    BoolQueryBuilder payedTag = QueryBuilders.boolQuery();
                    payedTag.must(QueryBuilders.termQuery("payed_60", query.getPayed60()));
                    actionProductProcess(payedTag, query, OrderPayedRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(payedTag);
                }
                if (null != query.getPayed90() && query.getPayed90() > 0L) {
                    BoolQueryBuilder payedTag = QueryBuilders.boolQuery();
                    payedTag.must(QueryBuilders.termQuery("payed_90", query.getPayed90()));
                    actionProductProcess(payedTag, query, OrderPayedRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(payedTag);
                }
                if (null != query.getPayed180() && query.getPayed180() > 0L) {
                    BoolQueryBuilder payedTag = QueryBuilders.boolQuery();
                    payedTag.must(QueryBuilders.termQuery("payed_180", query.getPayed180()));
                    actionProductProcess(payedTag, query, OrderPayedRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(payedTag);
                }
                if (null != query.getPayedAll() && query.getPayedAll() > 0L) {
                    BoolQueryBuilder payedTag = QueryBuilders.boolQuery();
                    payedTag.must(QueryBuilders.termQuery("payed_all", query.getPayedAll()));
                    actionProductProcess(payedTag, query, OrderPayedRuleCondition.CONDITION_CODE);
                    query.setHasShould(true);
                    builder.should(payedTag);
                }

                BoolQueryBuilder orderConsultTagBuilder = QueryBuilders.boolQuery();
                if (null != query.getHasMessage30() && query.getHasMessage30() > 0L) {
                    orderConsultTagBuilder.must(QueryBuilders.termQuery("has_message_30", query.getHasMessage30()));
                }
                if (null != query.getNoMessage28() && query.getNoMessage28() > 0L) {
                    orderConsultTagBuilder.must(QueryBuilders.termQuery("no_message_28", query.getNoMessage28()));
                }
                if (null != query.getNoOrder28() && query.getNoOrder28() > 0L) {
                    orderConsultTagBuilder.must(QueryBuilders.termQuery("no_order_28", query.getNoOrder28()));
                }

                if (!CollectionUtils.isEmpty(orderConsultTagBuilder.should()) || !CollectionUtils.isEmpty(orderConsultTagBuilder.must())) {
                    query.setHasShould(true);
                    builder.should(orderConsultTagBuilder);
                }
            }
            if (query.getNonOrderConsultTag() != null) {
                query.setHasShould(true);
                builder.should(QueryBuilders.termQuery("un_order_30", 0L));
            }
        }
        if (query.getSex() != null) {
            builder.must(QueryBuilders.termQuery("sex", query.getSex()));
        }
        if (StringUtils.isNotBlank(query.getProvince())) {
            builder.must(QueryBuilders.termQuery("province.keyword", query.getProvince()));
        }
        if (StringUtils.isNotBlank(query.getCity())) {
            builder.must(QueryBuilders.termQuery("city.keyword", query.getCity()));
        }
        if (StringUtils.isNotBlank(query.getArea())) {
            builder.must(QueryBuilders.termQuery("area.keyword", query.getArea()));
        }
        BuyerPortraitQuery.OriginalCondition originalCondition = query.getOriginalCondition();
        if (originalCondition != null) {
            if (originalCondition.getStartPayedTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_payed").gte(originalCondition.getStartPayedTime()));
            }
            if (originalCondition.getEndPayedTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_payed").lte(originalCondition.getEndPayedTime()));
            }
            if (originalCondition.getStartConsulTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_consultation").gte(originalCondition.getStartConsulTime()));
            }
            if (originalCondition.getEndConsulTime() != null) {
                builder.must(QueryBuilders.rangeQuery("gmt_last_consultation").lte(originalCondition.getEndConsulTime()));
            }
            if (originalCondition.getMinOrderPayed() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed").gte(originalCondition.getMinOrderPayed()));
            }
            if (originalCondition.getMaxOrderPayed() != null) {
                builder.must(QueryBuilders.rangeQuery("order_payed").lte(originalCondition.getMaxOrderPayed()));
            }
            if (originalCondition.getMinOrderCount() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count").gte(originalCondition.getMinOrderCount()));
            }
            if (originalCondition.getMaxOrderCount() != null) {
                builder.must(QueryBuilders.rangeQuery("order_count").lte(originalCondition.getMaxOrderCount()));
            }
            if (originalCondition.getMinAverageProduct() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product").gte(originalCondition.getMinAverageProduct()));
            }
            if (originalCondition.getMaxAverageProduct() != null) {
                builder.must(QueryBuilders.rangeQuery("average_product").lte(originalCondition.getMaxAverageProduct()));
            }
            if (originalCondition.getMinProductCount() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count").gte(originalCondition.getMinProductCount()));
            }
            if (originalCondition.getMaxProductCount() != null) {
                builder.must(QueryBuilders.rangeQuery("product_count").lte(originalCondition.getMaxProductCount()));
            }
        }
        if (StringUtils.isNotBlank(query.getBuyerId())) {
            builder.must(QueryBuilders.matchQuery("buyer_id", query.getBuyerId()));
        }
        if (query.getSmsStatus() != null && query.getSmsStatus()) {
            builder.mustNot(QueryBuilders.termQuery("phone.keyword", ""));
        }
        // 排除product
        builder.mustNot(new HasParentQueryBuilder("buyer_portrait_calculate", QueryBuilders.matchAllQuery(), false));
        // 若没有 .should()条件配置，则下面的条件将永远不能实现
        if (query.isHasShould()) {
            builder.minimumShouldMatch(1);
        }
    }

    /**
     * 关联商品查询条件设置
     *
     * @param builder
     * @param query
     * @param conditonCode
     */
    private void actionProductProcess(BoolQueryBuilder builder, BuyerPortraitQuery query, int conditonCode) {
        List<BuyerPortraitQuery.LabelRule> labelRules = query.getLabelRules();
        if (CollectionUtils.isEmpty(labelRules)) {
            return;
        }
        for (BuyerPortraitQuery.LabelRule labelRule : labelRules) {
            if (labelRule.getConditionCode() != conditonCode) {
                continue;
            }
            // 关联商品查询
            if (labelRule.getProductAssociatedType() != null) {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                // 全部商品
                if (labelRule.getProductAssociatedType().equals(RelatedProductTypeEnum.ALL.getValue())) {
                    // do nothing
                }
                // 指定商品
                if (labelRule.getProductAssociatedType().equals(RelatedProductTypeEnum.PRODUCT.getValue())) {
                    // 关联查询产品数据，包含指定商品ids
                    boolQueryBuilder.must(QueryBuilders.termsQuery("product_iid.keyword", labelRule.getProductIds()));
                    boolQueryBuilder.must(QueryBuilders.termQuery("action_type", labelRule.getActionType()));
                    // 关联商品时间在指定时间内
                    if (labelRule.getProductAssociatedStartDate() != null) {
                        boolQueryBuilder.must(QueryBuilders.rangeQuery("gmt_action_time").from(labelRule.getProductAssociatedStartDate()));
                    }
                }
                if (!CollectionUtils.isEmpty(boolQueryBuilder.must())) {
                    HasChildQueryBuilder hasChildQueryBuilder = new HasChildQueryBuilder("product", boolQueryBuilder, ScoreMode.None);
                    builder.must(hasChildQueryBuilder);
                }
            }
        }
    }
}