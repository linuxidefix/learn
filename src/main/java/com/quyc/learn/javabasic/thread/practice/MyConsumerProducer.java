package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者
 * Created by quyuanchao on 2019/3/17 13:28.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MyConsumerProducer {

    public static void main(String[] args) {
//        BlockingQueue<Integer> integers = new ArrayBlockingQueue<>(3);
        MyBlockingQueuePractice<Integer> integers = new MyBlockingQueuePractice<>(3);
        MyProducer producer = new MyProducer(integers,100);
        MyConsumer consumer = new MyConsumer(integers);
        for (int i = 0; i < 9; i++) {
            new Thread(producer).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(consumer).start();
        }
    }

}

class MyProducer implements Runnable {

    private MyBlockingQueuePractice<Integer> queue;
    private static AtomicInteger i = new AtomicInteger(0);
    private int max;

    public MyProducer(MyBlockingQueuePractice<Integer> queue, int max) {
        this.queue = queue;
        this.max = max;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    if (i.intValue() > max) {
                        break;
                    }
                    System.out.println("producer i = " + i);
                    queue.enqueue(i.getAndIncrement());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyConsumer implements Runnable {
    private MyBlockingQueuePractice<Integer> queue;
    private Integer i;

    public MyConsumer(MyBlockingQueuePractice<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    Integer integer = queue.dequeue();
                    System.out.println("consumer i = " + integer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}