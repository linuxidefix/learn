package com.quyc.learn.es.document;

import com.alibaba.fastjson.JSON;
import com.quyc.learn.es.EsClientUtil;
import com.quyc.learn.es.entity.Child;
import com.quyc.learn.es.entity.Parent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: andy
 * @create: 2019/7/18 19:13
 * @description:
 */
@Slf4j
public class JoinApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();
    private static String INDEX_NAME = "parent_child_1";
    private static String TYPE_NAME = "doc";

    public static void main(String[] args) throws Exception {
//        indexParent();
        indexChildren();
    }

    // 每一个BulkRequest执行时都会触发该Listener
    private static BulkProcessor.Listener listener = new BulkProcessor.Listener() {
        @Override
        public void beforeBulk(long executionId, BulkRequest request) {
            int numberOfActions = request.numberOfActions();
            log.debug("Executing bulk [{}] with {} requests",
                    executionId, numberOfActions);
        }

        @Override
        public void afterBulk(long executionId, BulkRequest request,
                              BulkResponse response) {
            if (response.hasFailures()) {
                log.warn("Bulk [{}] executed with failures", executionId);
            } else {
                log.info("Bulk [{}] completed in {} milliseconds",
                        executionId, response.getTook().getMillis());
            }
        }

        @Override
        public void afterBulk(long executionId, BulkRequest request,
                              Throwable failure) {
            log.error("Failed to execute bulk", failure);
        }
    };

    private static List<Parent> parents = new ArrayList<>() {
        {
            Random random = new Random(13);
            add(new Parent("James", "Java programmer", "male", 43, 2000, "this is a good gay", random.nextInt(10000)));
            add(new Parent("Brittany", "Java programmer", "female", 23, 1500, "You can use the term query to find d", random.nextInt(10000)));
            add(new Parent("Donny", "Java programmer", "male", 33, 1800, " Elasticsearch changes the values of text fi", random.nextInt(10000)));
            add(new Parent("Jonie", "Java programmer", "female", 32, 1600, "including whitespace and capitalization", random.nextInt(10000)));
            add(new Parent("Hervey", "Java programmer", "male", 22, 1200, "Boost values are relative to the default value", random.nextInt(10000)));
            add(new Parent("Jaimie", "Java programmer", "female", 27, 1900, "decreases the relevance score", random.nextInt(10000)));
            add(new Parent("Randall", "Java programmer", "male", 30, 2300, " A value greater than 1.0 increases the relevance score", random.nextInt(10000)));
            add(new Parent("Corrina", "Java programmer", "female", 35, 1700, "To better search text fields, the match query also analyzes your provided search term before performing a search", random.nextInt(10000)));
            add(new Parent("Dene", "Java programmer", "male", 33, 2000, "This means the match query can search text fields for analyzed tokens rather than an exact term.", random.nextInt(10000)));
            add(new Parent("Pam", "Java programmer", "female", 34, 1300, "The term query does not analyze the search term.", random.nextInt(10000)));
        }
    };

    private static List<Child> children = new ArrayList<>() {
        {
            try {
                add(new Child("James_1", 10, "female", "new york", DateUtils.parseDate("2009-07-11", "yyyy-MM-dd")));
                add(new Child("Brittany_1", 10, "male", "beijing", DateUtils.parseDate("2009-03-11", "yyyy-MM-dd")));
                add(new Child("Donny_1", 7, "male", "hangzhou", DateUtils.parseDate("2012-07-11", "yyyy-MM-dd")));
                add(new Child("Jonie_1", 10, "female", "shanghai", DateUtils.parseDate("2009-07-11", "yyyy-MM-dd")));
                add(new Child("Hervey_1", 7, "male", "beijing", DateUtils.parseDate("2012-07-11", "yyyy-MM-dd")));
                add(new Child("Jaimie_1", 16, "male", "xian", DateUtils.parseDate("2003-07-11", "yyyy-MM-dd")));
                add(new Child("Randall_1", 15, "male", "shanghai", DateUtils.parseDate("2014-07-11", "yyyy-MM-dd")));
                add(new Child("Corrina_1", 17, "female", "chongqing", DateUtils.parseDate("2012-07-11", "yyyy-MM-dd")));
                add(new Child("Dene_1", 10, "male", "lasa", DateUtils.parseDate("2009-02-11", "yyyy-MM-dd")));
                add(new Child("Pam_1", 10, "female", "chengdu", DateUtils.parseDate("2009-03-11", "yyyy-MM-dd")));
                add(new Child("James_2", 12, "female", "chongqing", DateUtils.parseDate("2007-07-11", "yyyy-MM-dd")));
                add(new Child("Brittany_2", 11, "male", "zhengzhou", DateUtils.parseDate("2008-07-11", "yyyy-MM-dd")));
                add(new Child("Donny_2", 16, "male", "luoyang", DateUtils.parseDate("2013-11-11", "yyyy-MM-dd")));
                add(new Child("Jonie_2", 17, "female", "xuchang", DateUtils.parseDate("2002-07-11", "yyyy-MM-dd")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    public static void indexParent() throws InterruptedException {
        BulkProcessor bulkProcessor = BulkProcessor.builder((bulkRequest, bulkResponseActionListener) ->
                client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener), listener).build();
        for (Parent parent : parents) {
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME, String.valueOf(parent.getId()));
            indexRequest.source(JSON.toJSONString(parent), XContentType.JSON);
            bulkProcessor.add(indexRequest);
        }
        bulkProcessor.awaitClose(10L, TimeUnit.SECONDS);
    }

    public static void indexChildren() throws InterruptedException {
        BulkProcessor bulkProcessor = BulkProcessor.builder((bulkRequest, bulkResponseActionListener) ->
                client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener), listener).build();
        for (int i = 0; i < children.size(); i++) {
            Child child = children.get(i);
            String parentId = String.valueOf(parents.get(i % parents.size()).getId());
            child.setJoinField(new Child.JoinField(parentId));
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME);
            indexRequest.source(JSON.toJSONString(child), XContentType.JSON);
            indexRequest.routing(parentId);
            bulkProcessor.add(indexRequest);
        }
        bulkProcessor.awaitClose(10L, TimeUnit.SECONDS);
    }

}
