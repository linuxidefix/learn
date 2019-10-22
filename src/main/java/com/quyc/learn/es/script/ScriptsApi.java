package com.quyc.learn.es.script;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.admin.cluster.storedscripts.DeleteStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptResponse;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.StoredScriptSource;

import java.io.IOException;

/**
 * @author: andy
 * @create: 2019/7/4 11:20
 * @description: ElasticSearch Script Api
 */
public class ScriptsApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
//        putStoredScript();
//        putStoredScript2();
        getStoredScript();
//        delStoredScript();
    }

    /**
     * 添加一个模板
     *
     * @throws IOException
     */
    public static void putStoredScript() throws IOException {
        PutStoredScriptRequest request = new PutStoredScriptRequest();
        request.id("script_1");
        request.content(new BytesArray("{" +
                "  \"script\": {" +
                "    \"lang\": \"mustache\"," +
                "    \"source\": {" +
                "      \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                "      \"size\" : \"{{size}}\"" +
                "    }" +
                "  }" +
                "}"), XContentType.JSON);
        AcknowledgedResponse response = client.putScript(request, RequestOptions.DEFAULT);
        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
    }

    /**
     * 添加一个模板
     *
     * @throws IOException
     */
    public static void putStoredScript2() throws IOException {
        PutStoredScriptRequest request = new PutStoredScriptRequest();
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("script");
            {

                builder.field("lang", "painless");
                builder.field("source", "Math.log(_score * 2) + params.multiplier");
            }
            builder.endObject();
        }
        builder.endObject();
        request.content(BytesReference.bytes(builder), XContentType.JSON);
        request.id("script_2");
        AcknowledgedResponse response = client.putScript(request, RequestOptions.DEFAULT);
        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
    }

    /**
     * 获取模板
     *
     * @throws IOException
     */
    public static void getStoredScript() throws IOException {
        GetStoredScriptRequest request = new GetStoredScriptRequest("script_2");
        GetStoredScriptResponse response = client.getScript(request, RequestOptions.DEFAULT);
        System.out.println("response = " + response);
        StoredScriptSource source = response.getSource();
        System.out.println("source.getSource() = " + source.getSource());
    }

    /**
     * 删除模板
     *
     * @throws IOException
     */
    public static void delStoredScript() throws IOException {
        DeleteStoredScriptRequest request = new DeleteStoredScriptRequest("script_1");
        AcknowledgedResponse response = client.deleteScript(request, RequestOptions.DEFAULT);
        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
    }

}
