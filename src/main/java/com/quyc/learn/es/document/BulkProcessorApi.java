package com.quyc.learn.es.document;

import com.alibaba.fastjson.JSON;
import com.quyc.learn.es.EsClientUtil;
import com.quyc.learn.es.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: andy
 * @create: 2019/7/1 14:55
 * @description: ElasticSearch BulkProcessorApi
 */
@Slf4j
public class BulkProcessorApi {

    private static RestHighLevelClient client = EsClientUtil.getClient();

    public static void main(String[] args) throws InterruptedException {
//        bulkProcessor();
        indexPerson();
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

    public static void bulkProcessor() throws InterruptedException {

        // 构建BulkProcessor
        // 异步创建
//        BulkProcessor.Builder builder = BulkProcessor.builder((bulkRequest, bulkResponseActionListener)
//                -> client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener), listener);
        // 同步创建
        BulkProcessor.Builder builder = BulkProcessor.builder((bulkRequest, bulkResponseActionListener)
                -> {
            try {
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, listener);
        // 多少个操作之后刷新一个新的BulkRequest，默认1000，-1：disable
        builder.setBulkActions(500);
        // 多大数据量之后刷新一个新的BulkRequest，默认5M，-1：disable
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        // 并行Request数量，默认1，0：只允许一个Request
        builder.setConcurrentRequests(0);
        // 隔多少时间刷新一个新的BulkRequest，默认无
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        // 补偿策略，当一个BulkRequest被拒绝时，1s后重试3次，初始化为 BackoffPolicy.exponentialBackoff()
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));
        BulkProcessor bulkProcessor = builder.build();
        // 准备创建index数据
        IndexRequest one = new IndexRequest("posts", "doc", "8").
                source(XContentType.JSON, "title",
                        "In which order are my Elasticsearch queries executed?").opType(DocWriteRequest.OpType.CREATE);
        IndexRequest two = new IndexRequest("posts", "doc", "9")
                .source(XContentType.JSON, "title",
                        "Current status and upcoming changes in Elasticsearch");
        IndexRequest three = new IndexRequest("posts", "doc", "10")
                .source(XContentType.JSON, "title",
                        "The Future of Federated Search in Elasticsearch");
        bulkProcessor.add(one).add(two).add(three);
        // 直接关闭
//        bulkProcessor.close();
        // 定时关闭，true：即是结束所有BulkRequest发送完毕，false：相反
        boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);

    }

    private static List<Person> javaProgrammers = new ArrayList<>() {
        {
            int i = 0;
            while (i++ < 1000) {
                add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000, "this is a good gay", "北京市海淀区知春路1号创新大厦"));
                add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500, "You can use the term query to find d", "北京市通州区新华联家园31号楼"));
                add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800, " Elasticsearch changes the values of text fi", "河南省郑州市龙湖区金秋乐园23号楼"));
                add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600, "including whitespace and capitalization", "河南省许昌市长葛市视察路电器厂"));
                add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200, "Boost values are relative to the default value", "浙江省杭州市余杭区文一西路9号"));
                add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900, "decreases the relevance score", "浙江省杭州市余杭区文一西路遥望大厦"));
                add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300, " A value greater than 1.0 increases the relevance score", "浙江省杭州市余杭区文二西路新城峯璟"));
                add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700, "To better search text fields, the match query also analyzes your provided search term before performing a search", "上海市浦东新区世纪大道1号"));
                add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000, "This means the match query can search text fields for analyzed tokens rather than an exact term.", "杭州市西湖区余杭塘路866号"));
                add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300, "The term query does not analyze the search term.", "杭州市江干区凯旋路268号"));
            }
        }
    };

    private static List<Person> phpProgrammers = new ArrayList<>() {
        {
//            add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550, "this is a good gay"));
//            add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200, "You can use the term query to find d"));
//            add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600, " Elasticsearch changes the values of text fi"));
//            add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1200, "including whitespace and capitalization"));
//            add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100, "Boost values are relative to the default value"));
//
//            add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300, "decreases the relevance score"));
//            add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1200, " A value greater than 1.0 increases the relevance score"));
//            add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1300, "To better search text fields, the match query also analyzes your provided search term before performing a search"));
//            add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600, "This means the match query can search text fields for analyzed tokens rather than an exact term."));
//            add(new Person("Evonne", "Shari", "PHP programmer", "female", 30, 1400, "The term query does not analyze the search term."));
//
//            add(new Person("James", "Lucy", "PHP programmer", "male", 41, 1800, "The term query does not analyze the search term."));
//            add(new Person("Luis", "Lily", "PHP programmer", "female", 23, 1500, "This means the match query can search text fields for analyzed tokens rather than an exact term."));
//            add(new Person("Lyrn", "Andy", "PHP programmer", "female", 45, 1600, "To better search text fields, the match query also analyzes your provided search term before performing a search"));
//            add(new Person("Jordon", "Michile", "PHP programmer", "male", 32, 1800, " A value greater than 1.0 increases the relevance score"));
//            add(new Person("Maggie", "Brussel", "PHP programmer", "female", 40, 1600, "decreases the relevance score"));
        }
    };

    public static void indexPerson() throws InterruptedException {
        BulkProcessor.Builder builder = BulkProcessor.builder((bulkRequest, bulkResponseActionListener) ->
                client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener), listener);
        builder.setFlushInterval(TimeValue.timeValueMillis(1000));
        BulkProcessor bulkProcessor = builder.build();
//        for (int i = 1; i <= phpProgrammers.size(); i++) {
//            IndexRequest indexRequest = new IndexRequest("person-php", "php", String.valueOf(i));
//            indexRequest.source(JSON.toJSONString(phpProgrammers.get(i - 1)), XContentType.JSON);
//            bulkProcessor.add(indexRequest);
//        }
        for (int i = 1; i <= javaProgrammers.size(); i++) {
            IndexRequest indexRequest = new IndexRequest("person-java", "java", String.valueOf(i));
            indexRequest.source(JSON.toJSONString(javaProgrammers.get(i - 1)), XContentType.JSON);
            bulkProcessor.add(indexRequest);
        }
        bulkProcessor.awaitClose(10L, TimeUnit.SECONDS);
    }

}
