package com.quyc.learn.kafka.boot.receivemsg;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author: andy
 * @create: 2019/11/6 20:16
 * @description:
 */
@Slf4j
//@Component
public class KafkaListenerValideted {

    /**
     * 使用domain接收格式转换后的消息
     *
     * @param user the user
     */
    @KafkaListener(topics = "topic1", groupId = "listenerJSON", errorHandler = "kafkaListenerErrorHandler",
            containerFactory = "kafkaJSONListenerContainerFactory")
    public void listenerJson(User user) {
        log.info("listenerJson user={}", user);
    }

    /**
     * 使用Validator校验消息，并配置异常处理bean
     *
     * @param user the user
     */
    @KafkaListener(topics = "topic1", groupId = "listenerValidated", errorHandler = "kafkaListenerErrorHandler",
            containerFactory = "kafkaJSONListenerContainerFactory")
    public void listenerValidated(@Payload @Valid User user) {
        log.info("listenerValidated user={}", user);
    }

    /**
     * 异常处理bean
     *
     * @return the kafka listener error handler
     */
    @Bean
    public KafkaListenerErrorHandler kafkaListenerErrorHandler() {
        return (message, exception) -> {
            log.error("message={}", message, exception);
            return null;
        };
    }

    /**
     * 消息域
     */
    @Data
    public static class User {
        @Size(min = 6, max = 20, message = "姓名长度异常")
        private String name;
        @Min(value = 0, message = "年龄不能小于0")
        private int age;
    }

}
