package com.quyc.learn.kafka.java;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: andy
 * @create: 2019/11/11 18:04
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class TopicConfig {

    private String topic;
}
