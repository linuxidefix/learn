package com.quyc.learn.kafka.boot.sendmsg;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 使用ReplyingKafkaTemplate实现 请求/响应 模式
 * @author: andy
 * @create: 2019/10/23 14:06
 * @description: ReplyingKafkaTemplate 学习
 * https://docs.spring.io/spring-kafka/docs/2.3.1.RELEASE/reference/html/#replying-template
 */
//@Configuration
public class ReplyingKafkaTemplateDemo {

    @Bean
    public ApplicationRunner runner(ReplyingKafkaTemplate<Integer, String, String> template) {
        return args -> {
            // 请求消息
            ProducerRecord<Integer, String> record = new ProducerRecord<>("kRequests", "foo");
            // 设置响应消息写入的topic，该topic需要与 repliesContainer 订阅的topic一致，否则无法消费
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "kReplies".getBytes()));
            // 发送消息并获取发送future
            RequestReplyFuture<Integer, String, String> replyFuture = template.sendAndReceive(record);
            // 同步获取发送结果
            SendResult<Integer, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
            System.out.println("Sent ok: " + sendResult.getRecordMetadata());
            // 同步获取回复结果
            ConsumerRecord<Integer, String> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
            System.out.println("Return value: " + consumerRecord.value());
        };
    }

    /**
     * 配置 ReplyingTemplate
     *
     * @param producerFactory  生产者工厂，发送消息到指定topic
     * @param repliesContainer 响应消息的消费者组，用于消费响应消息
     * @return the replying kafka template
     */
    @Bean
    public ReplyingKafkaTemplate<Integer, String, String> replyingTemplate(
            ProducerFactory<Integer, String> producerFactory, ConcurrentMessageListenerContainer<Integer, String> repliesContainer) {

        return new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
    }

    /**
     * 配置响应topic的消费者组
     *
     * @param containerFactory the container factory
     * @return the concurrent message listener container
     */
    @Bean
    public ConcurrentMessageListenerContainer<Integer, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<Integer, String> containerFactory) {

        ConcurrentMessageListenerContainer<Integer, String> repliesContainer =
                containerFactory.createContainer("kReplies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public NewTopic kRequests() {
        return new NewTopic("kRequests", 10, (short) 2);
//        return TopicBuilder.name("kRequests")
//                .partitions(10)
//                .replicas(2)
//                .build();
    }

    @Bean
    public NewTopic kReplies() {
        return new NewTopic("kReplies", 10, (short) 2);
//        return TopicBuilder.name("kReplies")
//                .partitions(10)
//                .replicas(2)
//                .build();
    }



}
