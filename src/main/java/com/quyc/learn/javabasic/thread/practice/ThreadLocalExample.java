package com.quyc.learn.javabasic.thread.practice;

/**
 * Created by quyuanchao on 2019-2-20 17:06.
 * <p>Title: com.review.thread</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadLocalExample {
    public static void main(String[] args) {
        ThreadLocal threadLocal1 = new ThreadLocal();
        ThreadLocal threadLocal2 = new ThreadLocal();
        Thread thread1 = new Thread(() -> {
            threadLocal1.set(1);
            threadLocal2.set(1);
            System.out.println("thread1.threadLocal1.get() = " + threadLocal1.get());
            System.out.println("thread1.threadLocal2.get() = " + threadLocal2.get());
        });
        Thread thread2 = new Thread(() -> {
            threadLocal1.set(2);
            threadLocal2.set(2);
            System.out.println("thread2.threadLocal1.get() = " + threadLocal1.get());
            System.out.println("thread2.threadLocal2.get() = " + threadLocal2.get());
        });
        thread1.start();
        thread2.start();
    }
}
