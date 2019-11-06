package com.quyc.learn.kafka.boot.receivemsg;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

/**
 * @author: andy
 * @create: 2019/10/23 20:45
 * @description: KafkaListener 学习
 */
@Slf4j
//@Component
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
     * 定义一个kafka批量消息消费者
     *
     * @param msgs      消息净荷
     * @param offset    the offset
     * @param key       key
     * @param partition 分区
     * @param topic     主题
     * @param timestamp 时间戳
     */
    @KafkaListener(topics = "topic1", groupId = "batchListener", containerFactory = "batchKafkaListenerContainerFactory")
    public void batchListener(List<String> msgs,
                              @Header(KafkaHeaders.OFFSET) String offset,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
        log.info("batchListener received msg={},offset={},key={},partition={},topic={},timestamp={}", msgs, offset, key, partition, topic, timestamp);
    }

    /**
     * 定义一个kafka批量消息消费者
     *
     * @param msgs 消息净荷
     */
    @KafkaListener(topics = "topic1", groupId = "batchListenerMsg", containerFactory = "batchKafkaListenerContainerFactory")
    public void batchListenerMsg(List<Message<String>> msgs) {
        log.info("batchListenerMsg received msg={},", msgs);
        msgs.forEach(System.out::println);
    }

    /**
     * 定义一个kafka批量消息消费者
     *
     * @param crs 消息
     */
    @KafkaListener(topics = "topic1", groupId = "batchListenerCR", containerFactory = "batchKafkaListenerContainerFactory")
    public void batchListenerCR(List<ConsumerRecord<String, String>> crs) {
        log.info("batchListenerCR received crs={},", crs);
        crs.forEach(System.out::println);
    }

    /**
     * 定义一个kafka批量消息消费者
     * 1. 通过properties进行消费者属性配置可覆盖consumerFactory中配置的属性，支持如下格式：foo:bar, foo=bar, foo bar
     * 2. 2.0版本后，id可以作为groupId，并覆盖consumerFactory配置
     * @param crs 消息
     */
    @KafkaListener(id = "batchListenerCRS", topics = "topic1", containerFactory = "batchKafkaListenerContainerFactory",
            properties = {"max.poll.interval.ms:60000", ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=100"})
    public void batchListenerCRS(ConsumerRecords<String, String> crs) {
        log.info("batchListenerCRS received crs={},", crs);
        crs.partitions().forEach(System.out::println);
        crs.forEach(System.out::println);
    }


}
