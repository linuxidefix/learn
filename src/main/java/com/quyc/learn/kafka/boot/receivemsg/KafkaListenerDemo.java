package com.quyc.learn.kafka.boot.receivemsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: andy
 * @create: 2019/10/23 20:45
 * @description: KafkaListener 学习
 */
@Slf4j
@Component
public class KafkaListenerDemo {

    /**
     * 定义一个kafka消费者
     *
     * @param msg       消息净荷
     * @param offset    偏移量
     * @param key       key
     * @param partition 分区
     * @param topic     主题
     * @param timestamp 时间戳
     */
    @KafkaListener(topics = "topic1", concurrency = "3", groupId = "listener")
    public void listener(String msg,
                         @Header(KafkaHeaders.OFFSET) String offset,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
        log.info("listener received msg={},offset={},key={},partition={},topic={},timestamp={}", msg, offset, key, partition, topic, timestamp);
    }

    /**
     * 定义一个kafka消费者
     *
     * @param msgs      消息净荷
     * @param offset    the offset
     * @param key       key
     * @param partition 分区
     * @param topic     主题
     * @param timestamp 时间戳
     */
    @KafkaListener(topics = "topic1", concurrency = "3", groupId = "batchListener", containerFactory = "batchKafkaListenerContainerFactory")
    public void batchListener(List<String> msgs,
                              @Header(KafkaHeaders.OFFSET) String offset,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
        log.info("batchListener received msg={},offset={},key={},partition={},topic={},timestamp={}", msgs, offset, key, partition, topic, timestamp);
    }

}
