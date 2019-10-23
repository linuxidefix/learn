package com.quyc.learn.kafka.java;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author: andy
 * @create: 2019/10/22 20:51
 * @description: kafka listener
 */
@Slf4j
public class Listener {

    public final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = "annotated1")
    public void listen1(String foo) {
        log.info("received message：" + foo);
        this.latch1.countDown();
    }
}
