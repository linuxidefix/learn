package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;

import java.io.IOException;
import java.util.List;

/**
 * term vector 词向量
 * @author: andy
 * @create: 2019/7/1 11:51
 * @description: ElasticSearch TermVectorsApi
 */
public class TermVectorsApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws Exception {
        termVectors();
    }

    public static void termVectors() throws IOException {
        TermVectorsRequest request = new TermVectorsRequest("posts", "doc", "1");
        request.setFields("postDate");
        TermVectorsResponse response = client.termvectors(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        boolean found = response.getFound();
        for (TermVectorsResponse.TermVector tv : response.getTermVectorsList()) {
            String fieldname = tv.getFieldName();
            System.out.println("fieldname = " + fieldname);
            int docCount = tv.getFieldStatistics().getDocCount();
            System.out.println("docCount = " + docCount);
            long sumTotalTermFreq = tv.getFieldStatistics().getSumTotalTermFreq();
            System.out.println("sumTotalTermFreq = " + sumTotalTermFreq);
            long sumDocFreq = tv.getFieldStatistics().getSumDocFreq();
            System.out.println("sumDocFreq = " + sumDocFreq);
            if (tv.getTerms() != null) {
                List<TermVectorsResponse.TermVector.Term> terms = tv.getTerms();
                for (TermVectorsResponse.TermVector.Term term : terms) {
                    String termStr = term.getTerm();
                    System.out.println("termStr = " + termStr);
                    int termFreq = term.getTermFreq();
                    System.out.println("termFreq = " + termFreq);
                    int docFreq = term.getDocFreq();
                    System.out.println("docFreq = " + docFreq);
                    long totalTermFreq = term.getTotalTermFreq();
                    System.out.println("totalTermFreq = " + totalTermFreq);
                    float score = term.getScore();
                    System.out.println("score = " + score);
                    if (term.getTokens() != null) {
                        List<TermVectorsResponse.TermVector.Token> tokens = term.getTokens();
                        for (TermVectorsResponse.TermVector.Token token : tokens) {
                            int position = token.getPosition();
                            System.out.println("position = " + position);
                            int startOffset = token.getStartOffset();
                            System.out.println("startOffset = " + startOffset);
                            int endOffset = token.getEndOffset();
                            System.out.println("endOffset = " + endOffset);
                            String payload = token.getPayload();
                            System.out.println("payload = " + payload);
                        }
                    }
                }
            }
        }
    }


}
