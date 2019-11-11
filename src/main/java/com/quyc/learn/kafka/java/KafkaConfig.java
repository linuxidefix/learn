package com.quyc.learn.kafka.java;

import com.quyc.learn.kafka.origin.MyConsumerAwareRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RangeAssignor;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: andy
 * @create: 2019/10/22 20:47
 * @description: kafka 配置
 */
@Configuration
@EnableKafka
public class KafkaConfig implements KafkaListenerConfigurer {

    @Resource
    private MyConsumerAwareRebalanceListener myConsumerAwareRebalanceListener;

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 设置消息过滤策略，可自定义消息是否过滤
        factory.setRecordFilterStrategy(consumerRecord -> false);
        // 重试机制
//        factory.setRetryTemplate();
//        factory.setRecoveryCallback();
        // 实现有状态的重试，未处理的消息会在下次poll()中返回并进行再次消费
        factory.setStatefulRetry(true);
        return factory;
    }

    /**
     * 具有消息转发功能的container
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> replyKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 配置回复template，可结合ReplyingKafkaTemplate实现请求/响应模式，以及配合@SendTo实现消息转发
        factory.setReplyTemplate(kafkaTemplate());
        // 配置在均衡监听器
        factory.getContainerProperties().setConsumerRebalanceListener(myConsumerAwareRebalanceListener);
        // 自定义replyHeader，指定header进行转发
        factory.setReplyHeadersConfigurer((headerName, headerValue) -> "reply_header".equals(headerName));
        return factory;
    }

    /**
     * 使用StringJsonMessageConverter进行消息类型转换的container
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaJSONListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 配置回复template，可结合ReplyingKafkaTemplate实现请求/响应模式，以及配合@SendTo实现消息转发
        factory.setReplyTemplate(kafkaTemplate());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    /**
     * 批处理消费者容器
     * 可同时获取一个批次消息
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> batchKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public static Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.237.128:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "default-group");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 200);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 使用循环分配分区
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, Collections.singletonList(RoundRobinAssignor.class));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    public static Map<String, Object> consumerConfigsRangeAssignor() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.237.128:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "default-group");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 200);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 默认使用区域分配
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, Collections.singletonList(RangeAssignor.class));
        return props;
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    public static Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.237.128:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * When you use Spring Boot with the validation starter, a LocalValidatorFactoryBean is auto-configured
     */
    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    /**
     * 通过@KafkaListener注册的消费者并不是由application context管理，而是由KafkaListenerEndpointRegistry这个基础bean进行管理
     * KafkaListenerEndpointRegistrar is a helper bean for registering KafkaListenerEndpoint with a KafkaListenerEndpointRegistry
     *
     * @param registrar
     */
    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        // 注册validator
        registrar.setValidator(validatorFactoryBean);
    }
}
