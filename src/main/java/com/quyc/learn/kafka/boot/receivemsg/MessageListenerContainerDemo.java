package com.quyc.learn.kafka.boot.receivemsg;

import com.quyc.learn.kafka.java.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: andy
 * @create: 2019/10/23 19:06
 * @description: MessageListenerContainer学习
 */
@Slf4j
@Component
public class MessageListenerContainerDemo {

    /**
     * 初始化单线程消费者容器
     */
    @PostConstruct
    public void initKafkaMessageListenerContainer() {
        log.info("init KafkaMessageListenerContainer");
        ContainerProperties containerProperties = new ContainerProperties("thing1");
        containerProperties.setMessageListener((MessageListener<Object, Object>) message -> log.info("received msg = " + message));
        containerProperties.setGroupId("group-kafka-container");
        // 单线程的消息监听容器，一次只启动一个消费者
        KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(new DefaultKafkaConsumerFactory(KafkaConfig.consumerConfigs()), containerProperties);
        container.start();
    }

    /**
     * 初始化多线程消费者容器
     */
    @PostConstruct
    public void initConcurrentMessageListenerContainer() {
        log.info("init ConcurrentMessageListenerContainer");
        // 如果多线程监听器监听多个topic时，需要使用RoundRobinAssignor来分配partition，不然会使多个消费者分配不到分区
        ContainerProperties containerProperties = new ContainerProperties("thing2", "thing1");
        containerProperties.setGroupId("group-concurrent-kafka-container");
        containerProperties.setMessageListener((MessageListener<Object, Object>) data -> log.info("received msg = " + data));
        // 如果enable.auto.commit=true，则提交方式根据配置进行，若为false（2.3版本之后若配置文件未设置，则spring框架默认设置为false）则根据spring框架提供的提交方式进行
        // 消费者提交方式，默认批量提交
        containerProperties.setAckMode(ContainerProperties.AckMode.BATCH);
        // 异步提交偏移量，默认为true
        containerProperties.setSyncCommits(true);
        // 默认即为 LoggingCommitCallback
        containerProperties.setCommitCallback(new LoggingCommitCallback());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(new DefaultKafkaConsumerFactory(KafkaConfig.consumerConfigs()), containerProperties);
        // 多线程消息监听容器，一次启动可生成20个消费者
        container.setConcurrency(20);
        container.start();
    }


}
