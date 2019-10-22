package com.quyc.learn.javabasic.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by quyuanchao on 2019-2-15 14:58.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class SynchronizedExample {

    public void fun1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void fun2() {
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void fun3() {
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fun4() {
        synchronized (getClass()) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main1() {
        SynchronizedExample example = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example::fun1);
        executorService.execute(example::fun1);
    }

    public static void main2() {
        SynchronizedExample example1 = new SynchronizedExample();
        SynchronizedExample example2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example1::fun1);
        executorService.execute(example2::fun1);
    }

    public static void main3() {
        SynchronizedExample example1 = new SynchronizedExample();
        SynchronizedExample example2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example1::fun2);
        executorService.execute(example2::fun2);
    }

    public static void main4() {
        SynchronizedExample example = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example::fun2);
        executorService.execute(example::fun2);
    }

    public static void main5() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(SynchronizedExample::fun3);
        executorService.execute(SynchronizedExample::fun3);
    }

    public static void main6() {
        SynchronizedExample example1 = new SynchronizedExample();
        SynchronizedExample example2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example1::fun4);
        executorService.execute(example2::fun4);
    }

    public static void main7() {
        SynchronizedExample example = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(example::fun4);
        executorService.execute(example::fun4);
    }
    public static void main(String[] args) {
//        main1();
//        main2();
//        main3();
//        main4();
//        main5();
//        main6();
        main7();
    }

}
