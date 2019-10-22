package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数化查询
 *
 * @author: andy
 * @create: 2019/7/3 18:58
 * @description: ElasticSearch SearchTemplate Api
 */
public class SearchTemplateApi {


    private static RestHighLevelClient client = EsClientUtil.getClient();
    private static RestClient lowClient = EsClientUtil.getLowLevelClient();

    public static void main(String[] args) throws IOException {
//        inlineTemplate();
        registeredTemplate();
    }

    /**
     * Inline Template
     *
     * @throws IOException
     */
    public static void inlineTemplate() throws IOException {
        SearchTemplateRequest searchTemplateRequest = new SearchTemplateRequest(new SearchRequest("person"));
        searchTemplateRequest.setScriptType(ScriptType.INLINE);
        searchTemplateRequest.setScript("{" +
                "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                "  \"size\" : \"{{size}}\"" +
                "}");
        Map<String, Object> scriptsParams = new HashMap<>(3);
        scriptsParams.put("field", "gender");
        scriptsParams.put("value", "male");
        scriptsParams.put("size", 3);
        searchTemplateRequest.setScriptParams(scriptsParams);
        SearchTemplateResponse searchTemplateResponse = client.searchTemplate(searchTemplateRequest, RequestOptions.DEFAULT);
        System.out.println("searchTemplateResponse = " + searchTemplateResponse);
    }

    /**
     * 注册Scripts模板，并使用模板进行参数化查询
     */
    public static void registeredTemplate() throws IOException {
        Request scriptRequset = new Request("POST", "_scripts/title_search");
        scriptRequset.setJsonEntity("{" +
                "  \"script\": {" +
                "    \"lang\": \"mustache\"," +
                "    \"source\": {" +
                "      \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                "      \"size\" : \"{{size}}\"" +
                "    }" +
                "  }" +
                "}");
        Response response = lowClient.performRequest(scriptRequset);
        System.out.println("response = " + response);
        SearchTemplateRequest searchTemplateRequest = new SearchTemplateRequest(new SearchRequest("person"));
        searchTemplateRequest.setScriptType(ScriptType.STORED);
        searchTemplateRequest.setScript("title_search");
        Map<String, Object> scriptsParams = new HashMap<>(3);
        scriptsParams.put("field", "gender");
        scriptsParams.put("value", "male");
        scriptsParams.put("size", 3);
        searchTemplateRequest.setScriptParams(scriptsParams);
        SearchTemplateResponse searchTemplateResponse = client.searchTemplate(searchTemplateRequest, RequestOptions.DEFAULT);
        System.out.println("searchTemplateResponse = " + searchTemplateResponse);
        // 可设置该查询是一个模拟查询，并不实际执行
        searchTemplateRequest.setSimulate(true);
    }

}
