package com.quyc.learn.javabasic.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java 内存模型定义了 8 个操作来完成主内存和工作内存的交互操作。
 * read：把一个变量的值从主内存传输到工作内存中
 * load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中
 * use：把工作内存中一个变量的值传递给执行引擎
 * assign：把一个从执行引擎接收到的值赋给工作内存的变量
 * store：把工作内存的一个变量的值传送到主内存中
 * write：在 store 之后执行，把 store 得到的值放入主内存的变量中s
 *
 * Java 内存模型保证了 read、load、use、assign、store、write、lock 和 unlock 操作具有原子性，例如对一个 int 类型的变量执行 assign 赋值操作，这个操作就是原子性的。但是 Java 内存模型允许虚拟机将没有被 volatile 修饰的 64 位数据（long，double）的读写操作划分为两次 32 位的操作来进行，即 load、store、read 和 write 操作可以不具备原子性。
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadUnsafeExample {
    volatile int cnt = 0;

    public void add() {
        // 线程不安全
        cnt++;
    }

    public int get() {
        return cnt;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadSize = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();
        ThreadUnsafeExample example = new ThreadUnsafeExample();
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(example::add);
            countDownLatch.countDown();
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("example.cnt = " + example.cnt);
    }
}
