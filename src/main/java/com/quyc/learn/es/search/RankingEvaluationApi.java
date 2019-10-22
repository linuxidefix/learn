package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author: andy
 * @create: 2019/7/3 20:36
 * @description: ElasticSearch RankingEvaluation Api
 */
public class RankingEvaluationApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        rankingEvaluation();
    }

    public static void rankingEvaluation() throws IOException {
        EvaluationMetric evaluationMetric = new PrecisionAtK();
        List<RatedDocument> ratedDocuments = new ArrayList<>();
        ratedDocuments.add(new RatedDocument("person", "1", 1));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("firstName", "Vere"));
        RatedRequest ratedRequest = new RatedRequest("Vere_query", ratedDocuments, searchSourceBuilder);
        List<RatedRequest> ratedRequests = Collections.singletonList(ratedRequest);
        RankEvalSpec rankEvalSpec = new RankEvalSpec(ratedRequests, evaluationMetric);
        RankEvalRequest rankEvalRequest = new RankEvalRequest(rankEvalSpec, new String[]{"person"});
        RankEvalResponse rankEvalResponse = client.rankEval(rankEvalRequest, RequestOptions.DEFAULT);
        System.out.println("rankEvalResponse = " + rankEvalResponse);
        double evaluationResult = rankEvalResponse.getMetricScore();
        assertEquals(1.0 / 3.0, evaluationResult, 0.0);
        Map<String, EvalQueryQuality> partialResults =
                rankEvalResponse.getPartialResults();
        EvalQueryQuality evalQuality = partialResults.get("Vere_query");
        assertEquals("Vere_query", evalQuality.getId());
        double qualityLevel = evalQuality.metricScore();
        assertEquals(1.0 / 3.0, qualityLevel, 0.0);
        List<RatedSearchHit> hitsAndRatings = evalQuality.getHitsAndRatings();
        RatedSearchHit ratedSearchHit = hitsAndRatings.get(0);
        assertEquals("2", ratedSearchHit.getSearchHit().getId());
        assertFalse(ratedSearchHit.getRating().isPresent());
        MetricDetail metricDetails = evalQuality.getMetricDetails();
        String metricName = metricDetails.getMetricName();
        assertEquals(PrecisionAtK.NAME, metricName);
        PrecisionAtK.Detail detail = (PrecisionAtK.Detail) metricDetails;
        assertEquals(1, detail.getRelevantRetrieved());
        assertEquals(3, detail.getRetrieved());
    }

}
