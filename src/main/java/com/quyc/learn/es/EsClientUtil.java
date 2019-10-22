package com.quyc.learn.es;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyuanchao on 2019/6/30 15:08.
 * <p>Title: ElasticSearch Config</p>
 * <p>Description: </p>
 */
//@Configuration
public class EsClientUtil {

    private static CredentialsProvider credentialsProvider;

    static {
        credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("elastic", "Es!@#$%^"));
                new UsernamePasswordCredentials("elastic", "WEIer!@#$%^"));
    }

    private static class RestHighLevelClientHolder {
        private static final RestHighLevelClient ES_CLIENT = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("es-cn-45916s405000dd54p.public.elasticsearch.aliyuncs.com",
                RestClient.builder(new HttpHost("es-cn-mp91cbn3i000aeuw6.public.elasticsearch.aliyuncs.com",
                        9200, "http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })
        );
    }

    private static class RestLowLevelClientHolder {
        private static final RestClient ES_LOW_LEVEL_CLIENT = RestClient.builder(
//                new HttpHost("es-cn-mp91cbn3i000aeuw6.public.elasticsearch.aliyuncs.com", 9200, "http"))
                new HttpHost("es-cn-mp91cbn3i000aeuw6.public.elasticsearch.aliyuncs.com", 9200, "http"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();
    }

    public static final RestHighLevelClient getClient() {
        return RestHighLevelClientHolder.ES_CLIENT;
    }

    public static final RestClient getLowLevelClient() {
        return RestLowLevelClientHolder.ES_LOW_LEVEL_CLIENT;
    }

    /**
     * 从搜索结果解析数据
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getSearchResult(SearchResponse response, Class<T> clazz) {
        if (response == null) {
            return Lists.newArrayList();
        }
        if (response.status() != RestStatus.OK) {
            return Lists.newArrayList();
        }
        SearchHits hits = response.getHits();
        if (hits == null || hits.totalHits == 0L) {
            return Lists.newArrayList();
        }
        SearchHit[] searchHits = hits.getHits();
        List<T> result = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            T t = JSONObject.parseObject(hit.getSourceAsString(), clazz);
            result.add(t);
        }
        return result;
    }
}
