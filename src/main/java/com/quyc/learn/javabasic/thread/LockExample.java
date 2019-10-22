package com.quyc.learn.javabasic.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by quyuanchao on 2019-2-15 15:15.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class LockExample {

    private Lock lock = new ReentrantLock();

    public void fun1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockExample example = new LockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example::fun1);
        executorService.execute(example::fun1);
    }

}
