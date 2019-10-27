package com.quyc.learn.kafka.origin;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

/**
 * @progrem: learn
 * @description: 分区再均衡监听器
 * @author:
 * @create: 2019-10-27 22:46:17
 */
public class MyConsumerRebalanceListener implements ConsumerRebalanceListener {
    /**
     * 分区所有权收回前触发
     * @param collection
     */
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        // 提交最近处理成功的偏移量
    }

    /**
     * 新分区消费开始前出发
     * @param collection
     */
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        // 获取当前分区偏移量，一般用于偏移量保存在外部系统时调用
    }
}
