package com.quyc.learn.kafka.boot.sendmsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author: andy
 * @create: 2019/10/23 15:55
 * @description: 实现消息转发功能的Listener
 */
@Slf4j
//@Component
public class ReplyListener {

    /**
     * 该containerFactory必须设置ReplyTemplate
     * 配合ReplyingKafkaTemplate可实现 请求/响应 模式
     *
     * @param foo
     * @return
     */
    @KafkaListener(topics = "kRequests", containerFactory = "kafkaListenerContainerFactory")
    @SendTo
    public String listener(String foo) {
        // 收到消息后做业务处理，然后返回结果
        log.info("received message：" + foo + ", do something then reply");
        return "hello , i received message: " + foo;
    }

    /**
     * 该containerFactory必须设置ReplyTemplate
     *
     * @param foo
     * @return
     */
    @KafkaListener(topics = "myTopic", containerFactory = "kafkaListenerContainerFactory")
    @SendTo
    public String listener2(String foo) {
        log.info("received message：" + foo);
        return "hello , i received message: " + foo;
    }
}
