package com.quyc.learn.kafka.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author: andy
 * @create: 2019/10/23 11:39
 * @description: send msg to kafka
 */
@Controller
@Slf4j
public class SendMsgToKafka {

    @Autowired
    private KafkaTemplate template;

    /**
     * 异步发送消息
     *
     * @return
     */
    @GetMapping("sendToKafkaAsync")
    public String sendToKafkaAsync() {
        final ProducerRecord<String, String> record = createRecord();

        ListenableFuture<SendResult<Integer, String>> future = template.send(record);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(record);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(record, ex);
            }

        });
        return "success";
    }

    /**
     * 同步发送消息
     *
     * @return
     */
    @GetMapping("sendToKafkaSync")
    public String sendToKafkaSync() {
        final ProducerRecord<String, String> record = createRecord();

        try {
            template.send(record).get(10, TimeUnit.SECONDS);
            handleSuccess(record);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            handleFailure(record, e);
        }
        return "success";
    }

    private void handleFailure(ProducerRecord<String, String> record, Throwable ex) {
        log.error("send failure record={}", record, ex);
    }

    private void handleSuccess(ProducerRecord<String, String> record) {
        log.info("send success record={}", record);
    }

    private ProducerRecord<String, String> createRecord() {
        return new ProducerRecord<>("thing3", "test msg");
    }


}
