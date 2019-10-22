package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者
 * Created by quyuanchao on 2019/3/17 13:28.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ConsumerProducer {

    public static void main(String[] args) {
        BlockingQueue<Integer> integers = new ArrayBlockingQueue<>(3);
        Producer producer = new Producer(integers);
        Consumer consumer = new Consumer(integers);
        for (int i = 0; i < 7; i++) {
            new Thread(consumer).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(producer).start();
        }
    }

}

class Producer implements Runnable {

    private BlockingQueue<Integer> queue;
    private static AtomicInteger i = new AtomicInteger(0);

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println("producer i = " + i);
            queue.put(i.getAndIncrement());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private Integer i;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Integer integer = queue.take();
            System.out.println("consumer i = " + integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}