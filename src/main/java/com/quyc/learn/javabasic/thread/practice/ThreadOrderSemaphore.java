package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.Semaphore;

/**
 * n 个线程顺序打印 0-100
 * 采用 Semaphore
 * Created by quyuanchao on 2019/3/15 22:31.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadOrderSemaphore {

    private static int current = 0;

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        Semaphore[] semaphores = new Semaphore[n];
        for (int i = 0; i < n; i++) {
            semaphores[i] = new Semaphore(1);
            if (i != n - 1) {
//                semaphores[i].acquire();
            }
        }
        Thread[] threads = new Thread[n];
        for (int i = n-1; i >= 0; i--) {
            Semaphore lastSemaphore = i == 0 ? semaphores[n - 1] : semaphores[i - 1];
            Semaphore currentSemaphore = semaphores[i];
            int finalI = i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            lastSemaphore.acquire();
                            if (current > 100) {
                                System.exit(0);
                            }
                            System.out.println("thread " + finalI + ": " + current++);
                            currentSemaphore.release();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }


    }
}
