package com.quyc.learn.javabasic.thread.practice;

/**
 * Created by quyuanchao on 2019/3/17 20:56.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadOrderPractice implements Runnable {

    private static int current;
    private static final int MAX = 100;
    private int threadNo;
    private int threadCount;

    public ThreadOrderPractice(int threadNo, int threadCount) {
        this.threadNo = threadNo;
        this.threadCount = threadCount;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ThreadOrderPractice.class) {
                while (current % threadCount != threadNo) {
                    try {
                        ThreadOrderPractice.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + ": " + current++);
                if (current > MAX) {
                    System.exit(0);
                }
                ThreadOrderPractice.class.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 3;
        for (int i = 0; i < threadCount; i++) {
            new Thread(new ThreadOrderPractice(i, threadCount)).start();
        }
    }
}
