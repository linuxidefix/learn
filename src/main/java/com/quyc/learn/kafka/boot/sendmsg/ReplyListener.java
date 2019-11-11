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
@Component
public class ReplyListener {

    /**
     * 该containerFactory必须设置ReplyTemplate
     * 配合ReplyingKafkaTemplate可实现 请求/响应 模式
     *
     * @param foo
     * @return
     */
//    @KafkaListener(topics = "kRequests", containerFactory = "replyKafkaListenerContainerFactory")
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
    @KafkaListener(topics = "sendTopic", containerFactory = "replyKafkaListenerContainerFactory")
    // 配置静态topic
//    @SendTo("topic1")
    // 配置config time SpEL
//    @SendTo("${kafka.topic}")
    // 配置config time SpEL，读取bean配置
//    @SendTo("#{topicConfig.topic}")
    // 配置 runtime SpEL
//    @SendTo("!{someExpression}") routes to the topic determined by evaluating the expression at runtime. The #root object for the evaluation has three properties:
//    request: The inbound ConsumerRecord (or ConsumerRecords object for a batch listener))
//    source: The org.springframework.messaging.Message<?> converted from the request.
    // 转发到方法返回结果指定的topic上，值为方法结果，及topic名
//    result: The method return result.
//    @SendTo("!{request.value()}")
//    @SendTo("!{source.getPayload()}")
    // 根据header中的reply_topic指定的topic名转发
    @SendTo
    public String listener2(String foo) {
        log.info("received message：" + foo);
        return "hello , i received message: " + foo;
    }
}
