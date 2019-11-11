package com.quyc.learn.kafka.origin;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author: andy
 * @create: 2019/11/11 17:03
 * @description: 在均衡监听器实现
 */
@Component
@Slf4j
public class MyConsumerAwareRebalanceListener implements ConsumerAwareRebalanceListener {

    /**
     * The same as {@link #onPartitionsRevoked(Collection)} with the additional consumer
     * parameter. It is invoked by the container before any pending offsets are committed.
     *
     * @param consumer   the consumer.
     * @param partitions the partitions.
     */
    @Override
    public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        // do nothing
        log.info("onPartitionsRevokedBeforeCommit");
    }

    /**
     * The same as {@link #onPartitionsRevoked(Collection)} with the additional consumer
     * parameter. It is invoked by the container after any pending offsets are committed.
     *
     * @param consumer   the consumer.
     * @param partitions the partitions.
     */
    @Override
    public void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        // do nothing
        log.info("onPartitionsRevokedAfterCommit");
    }

    /**
     * The same as {@link #onPartitionsAssigned(Collection)} with the additional consumer
     * parameter.
     *
     * @param consumer   the consumer.
     * @param partitions the partitions.
     */
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        // do nothing
    }
}
