package com.quyc.learn.kafka.origin;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @progrem: learn
 * @description:
 * @author:
 * @create: 2019-10-27 16:18:17
 */
@Slf4j
public class OriginDemo {

    private static Properties kafkaProducerPros = new Properties();
    static {
        kafkaProducerPros.put("broker.server", "localhost:9092");
        kafkaProducerPros.put("key.seralizer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducerPros.put("value.seralizer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    private static Properties kafkaConsumerPros = new Properties();
    static {
        kafkaConsumerPros.put("bootstrap.servers", "localhost:9092");
        kafkaConsumerPros.put("group.id","group_1");
        kafkaConsumerPros.put("key.deserializer", "kafka.consumer.keyDeserializerClass");
        kafkaConsumerPros.put("value.deserializer", "kafka.consumer.valueDeserializerClass");
    }

    /**
     * 仅发送
     */
    public void send() {
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerPros);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic1", "hello world");
        kafkaProducer.send(producerRecord);
    }

    /**
     * 同步发送
     */
    public void sendSync() {
        try {
            KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerPros);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic1", "hello world");
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步发送
     */
    public void sendAsync() {
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProducerPros);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic1", "hello world");
        kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
            System.out.println("recordMetadata = " + recordMetadata);
            if (e != null) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 消费者轮询获取消息
     */
    public void consumer() {
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerPros);
        kafkaConsumer.subscribe(Lists.newArrayList("topic1", "topic2"));
        try {
            while (true) {
                ConsumerRecords<String,String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    log.info("topic={},partition={},offset={},key={},value={}", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            kafkaConsumer.close();
        }
    }

    /**
     * 主动提交偏移量
     */
    public void commitSync() {
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerPros);
        kafkaConsumer.subscribe(Lists.newArrayList("topic1", "topic2"));
        try {
            while (true) {
                ConsumerRecords<String,String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    log.info("topic={},partition={},offset={},key={},value={}", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
                kafkaConsumer.commitSync();
            }
        } catch (Exception e) {
            kafkaConsumer.close();
        }
    }

    /**
     * 异步提交偏移量
     */
    public void commit() {
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerPros);
        kafkaConsumer.subscribe(Lists.newArrayList("topic1", "topic2"));
        try {
            while (true) {
                ConsumerRecords<String,String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    log.info("topic={},partition={},offset={},key={},value={}", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
                kafkaConsumer.commitAsync();
            }
        } catch (Exception e) {
            kafkaConsumer.commitSync();
            kafkaConsumer.close();
        }
    }

}
