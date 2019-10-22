package com.quyc.learn.es.document;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: andy
 * @create: 2019/7/3 9:55
 * @description:
 */
public class ParentIndex {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws Exception {
//        indexParent();
        indexChild();
    }

    public static void indexParent() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        Map son = new HashMap();
        jsonMap.put("text", "this is a question");
        jsonMap.put("andi_index_field", "question");
        IndexRequest indexRequest = new IndexRequest("andi_index", "_doc", "4").source(jsonMap);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    public static void indexChild() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, String> son = new HashMap<>();
        jsonMap.put("text", "this is an answer");
        son.put("name", "answer");
        son.put("parent", "1");
        jsonMap.put("andi_index_field", son);
        IndexRequest indexRequest = new IndexRequest("andi_index", "_doc", "2").source(jsonMap);
        indexRequest.routing("4");
//        indexRequest.parent("4");
        client.index(indexRequest, RequestOptions.DEFAULT);
    }


}
