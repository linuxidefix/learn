package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by quyuanchao on 2019/3/17 20:01.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ConsumerProducerPractice {
    public static void main(String[] args) {
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(5);
        for (int i = 0; i < 6; i++) {
            new Thread(new NewProducer(strings)).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new NewConsumer(strings)).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(new NewProducer(strings)).start();
        }
    }
}

class NewProducer implements Runnable {

    private BlockingQueue<String> queue ;
    private static AtomicInteger integer = new AtomicInteger(0);
    public NewProducer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.put("producer");
                System.out.println(" produce ");
                integer.getAndIncrement();
                if (integer.intValue() > 10) {
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class NewConsumer implements Runnable {

    private BlockingQueue<String> queue ;
    public NewConsumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String take = queue.take();
                System.out.println(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}