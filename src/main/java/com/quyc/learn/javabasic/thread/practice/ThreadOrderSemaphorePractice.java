package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.Semaphore;

/**
 * Created by quyuanchao on 2019/3/17 21:02.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadOrderSemaphorePractice implements Runnable {

    private static int current;
    private int max;
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;

    public ThreadOrderSemaphorePractice(int max, Semaphore mySemaphore, Semaphore nextSemaphore) {
        this.max = max;
        this.mySemaphore = mySemaphore;
        this.nextSemaphore = nextSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mySemaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ": " + current++);
                if (current > max) {
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                nextSemaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        Semaphore[] semaphores = new Semaphore[10];
        for (int i = 0; i < n; i++) {
            semaphores[i] = new Semaphore(1);
            if (i != 0) {
                semaphores[i].acquire();
            }
        }
        for (int i = 0; i < n; i++) {
            if (i != n - 1) {
                new Thread(new ThreadOrderSemaphorePractice(100, semaphores[i], semaphores[i + 1])).start();
            } else {
                new Thread(new ThreadOrderSemaphorePractice(100, semaphores[i], semaphores[0])).start();
            }
        }
    }
}
