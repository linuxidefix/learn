package com.quyc.learn.es.script;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;

/**
 * 使用 Script 做查询匹配条件
 *
 * @author: andy
 * @create: 2019/7/9 11:52
 * @description:
 */
public class ScriptsExpression {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        testScript1();
    }

    public static void testScript1() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("multiplier", 1200);
        builder.query(QueryBuilders.scriptQuery(
                new Script(ScriptType.INLINE, "painless", "def multiplier = params.multiplier;\n" +
                        "      long value = doc['salary'].value & multiplier;\n" +
                        "      return value == multiplier;", params)));
        searchRequest.source(builder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("search = " + search);

    }

    public static void testScript2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("multiplier", 4);
        builder.query(QueryBuilders.scriptQuery(
                new Script(ScriptType.INLINE, "painless", "def multiplier = params.multiplier;\n" +
                        "      long value = ~(doc['orderConsultTag'].value) & multiplier;\n" +
                        "      return value == multiplier;", params)));
        searchRequest.source(builder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("search = " + search);

    }

}
