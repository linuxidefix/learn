package com.quyc.learn.kafka.boot.receivemsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: andy
 * @create: 2019/11/6 16:11
 * @description: @KafkaListener应用到类名上
 */
@KafkaListener(id = "multi", topics = "topic1", groupId = "multi")
@Component
@Slf4j
public class KafkaListenerDemo2 {


    /**
     * 通过转换后的消息净荷的类型判断执行哪一个KafkaHandler
     *
     * @param msg the msg
     */
    @KafkaHandler
    public void listenStr(String msg) {
        log.info("listenStr msg={}", msg);
    }

    @KafkaHandler
    public void listenInt(Integer msg) {
        log.info("listenInt msg={}", msg);
    }

    /**
     * 指定默认处理器，若没有类型匹配的handler则使用该默认handler
     *
     * @param msg the msg
     */
    @KafkaHandler(isDefault = true)
    public void listenObj(Object msg) {
        log.info("listenObj msg={}", msg);
    }
}
