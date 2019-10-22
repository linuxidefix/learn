package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 在一个 SearchRequest 中同时执行多个 SearchTemplate
 *
 * @author: andy
 * @create: 2019/7/3 20:00
 * @description: ElasticSearch MultiSearchTemplate Api
 */
public class MultiSearchTemplateApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        multiSearchTemplate();
    }

    public static void multiSearchTemplate() throws IOException {
        String[] searchTerms = {"Vere", "Jayden", "Addison"};
        // 创建 MultiSearchTemplateRequest
        MultiSearchTemplateRequest multiRequest = new MultiSearchTemplateRequest();
        for (String searchTerm : searchTerms) {
            SearchTemplateRequest request = new SearchTemplateRequest(new SearchRequest("person"));
            request.setScriptType(ScriptType.STORED);
            // 使用之前已经保存的 scripts
            request.setScript("title_search");
            Map<String, Object> scriptParams = new HashMap<>();
            scriptParams.put("field", "firstName");
            scriptParams.put("value", searchTerm);
            scriptParams.put("size", 5);
            request.setScriptParams(scriptParams);
            multiRequest.add(request);
        }
        MultiSearchTemplateResponse response = client.msearchTemplate(multiRequest, RequestOptions.DEFAULT);
        System.out.println("response = " + response);
    }

}
