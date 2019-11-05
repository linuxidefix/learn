package com.quyc.learn.kafka.boot.receivemsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: andy
 * @create: 2019/10/23 20:45
 * @description: KafkaListener 学习
 */
@Slf4j
@Component
public class KafkaListenerDemo {

    @KafkaListener(id = "listener-1", topics = "topic1", concurrency = "3", groupId = "listener-1")
    public void listen(String msg) {
        log.info("listener-1 received msg = " + msg);
    }

}
