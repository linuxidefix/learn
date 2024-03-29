package com.quyc.learn.kafka.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author: andy
 * @create: 2019/10/22 20:53
 * @description:
 */
//@RestController
public class KafkaDemo {

    @Autowired
    private Listener listener;
    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate template;

    @PostMapping("sendByJava")
    public String sendByJava() throws InterruptedException {
        template.send("annotated1", 0, "foo");
        template.flush();
        assertTrue(this.listener.latch.await(10, TimeUnit.SECONDS));
        return "success";
    }
}
