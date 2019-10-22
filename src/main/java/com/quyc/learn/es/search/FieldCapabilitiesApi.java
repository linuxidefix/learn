package com.quyc.learn.es.search;

import com.quyc.learn.es.EsClientUtil;
import org.elasticsearch.action.fieldcaps.FieldCapabilities;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

/**
 * 属性能力查询
 * @author: andy
 * @create: 2019/7/3 20:18
 * @description: ElasticSearch FieldCapabilities Api
 */
public class FieldCapabilitiesApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws IOException {
        fieldCapabilities();
    }

    public static void fieldCapabilities() throws IOException {
        FieldCapabilitiesRequest fieldCapabilitiesRequest = new FieldCapabilitiesRequest()
                .fields("firstName")
                .indices("person","copy_person");
        fieldCapabilitiesRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        FieldCapabilitiesResponse fieldCapabilitiesResponse = client.fieldCaps(fieldCapabilitiesRequest, RequestOptions.DEFAULT);
        System.out.println("fieldCapabilitiesResponse = " + fieldCapabilitiesResponse);
        Map<String, FieldCapabilities> userResponse = fieldCapabilitiesResponse.getField("firstName");
        System.out.println("userResponse = " + userResponse);
        FieldCapabilities textCapabilities = userResponse.get("text");
        System.out.println("textCapabilities = " + textCapabilities);
        boolean isSearchable = textCapabilities.isSearchable();
        System.out.println("isSearchable = " + isSearchable);
        boolean isAggregatable = textCapabilities.isAggregatable();
        System.out.println("isAggregatable = " + isAggregatable);
        String[] indices = textCapabilities.indices();
        String[] nonSearchableIndices = textCapabilities.nonSearchableIndices();
        String[] nonAggregatableIndices = textCapabilities.nonAggregatableIndices();
    }



}
