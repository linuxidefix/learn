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
//@Component
public class Listener {

    public final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "annotated1")
    public void listener(String foo) {
        log.info("received message：" + foo);
        this.latch.countDown();
    }
}
