package com.quyc.learn.kafka.java;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author: andy
 * @create: 2019/10/22 20:51
 * @description: kafka listener
 */
public class Listener {

    public final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = "annotated1")
    public void listen1(String foo) {
        this.latch1.countDown();
    }
}
