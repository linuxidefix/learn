package com.quyc.learn.kafka.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author: andy
 * @create: 2019/10/22 20:53
 * @description:
 */
@Component
public class KafkaDemo {

    @Autowired
    private Listener listener;
    @Autowired
    private KafkaTemplate template;

    @PostConstruct
    public void testJava() throws InterruptedException {
        template.send("annotated1", 0, "foo");
        template.flush();
        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }
}
