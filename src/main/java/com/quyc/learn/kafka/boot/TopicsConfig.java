package com.quyc.learn.kafka.boot;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: andy
 * @create: 2019/10/23 10:52
 * @description: 管理topic
 */
@Configuration
public class TopicsConfig {

    @Bean
    public NewTopic topic1() {
        NewTopic newTopic = new NewTopic("thing1", 10, (short) 3);
        return newTopic;
        // TopicBuilder 在2.3.1版本提供
//        return TopicBuilder.name("thing1")
//                .partitions(10)
//                .replicas(3)
//                .compact()
//                .build();
    }

    @Bean
    public NewTopic topic2() {
        NewTopic newTopic = new NewTopic("thing2", 10, (short) 3);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(TopicConfig.COMPRESSION_TYPE_CONFIG, "zxtd");
        newTopic.configs(hashMap);
        return newTopic;
//        return TopicBuilder.name("thing2")
//                .partitions(10)
//                .replicas(3)
//                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//                .build();
    }

    @Bean
    public NewTopic topic3() {
        Map<Integer, java.util.List<Integer>> map = new HashMap<>();
        // 自定义分区分配
        map.put(0, Arrays.asList(0, 1));
        map.put(1, Arrays.asList(1, 2));
        map.put(2, Arrays.asList(2, 0));
        NewTopic newTopic = new NewTopic("thing3", map);
        return newTopic;
//        return TopicBuilder.name("thing3")
//                .assignReplicas(0, Arrays.asList(0, 1))
//                .assignReplicas(1, Arrays.asList(1, 2))
//                .assignReplicas(2, Arrays.asList(2, 0))
//                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//                .build();
    }

}
