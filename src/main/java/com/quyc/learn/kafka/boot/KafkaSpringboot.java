package com.quyc.learn.kafka.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: andy
 * @create: 2019/10/22 20:57
 * @description:
 */
@Slf4j
@Controller
public class KafkaSpringboot {


    @Autowired
    private KafkaTemplate template;

    private final CountDownLatch latch = new CountDownLatch(3);

    @GetMapping("sendByBoot")
    public String sendByBoot() throws Exception {
        template.send("thing1", "foo1");
        template.send("thing1", "foo2");
        template.send("thing1", "foo3");
        latch.await(60, TimeUnit.SECONDS);
        log.info("All received");
        return "success";
    }

    @KafkaListener(topics = "thing1")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        log.info(cr.toString());
        latch.countDown();
    }

}
