package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.Semaphore;

/**
 * Created by quyuanchao on 2019/3/17 17:24.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadPractice3 implements Runnable {

    private static int current;
    private int max;
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;

    public ThreadPractice3(int max, Semaphore mySemaphore, Semaphore nextSemaphore) {
        this.max = max;
        this.mySemaphore = mySemaphore;
        this.nextSemaphore = nextSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mySemaphore.acquire();
                if (current>max) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + ": " + current++);
                nextSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore[] semaphores = new Semaphore[100];
        for (int i = 0; i < 100; i++) {
            semaphores[i] = new Semaphore(1);
            if (i != 0) {
                // 除了第一个锁之外，其他锁都被主线程持有，第二个线程只能等待第一个线程执行完后释放自己的锁，第一个线程等最后一个线程执行完后释放自己的锁
                semaphores[i].acquire();
            }
        }
        for (int i = 0; i < 10; i++) {
            if (i != 9) {
                new Thread(new ThreadPractice3(100, semaphores[i], semaphores[i + 1])).start();
            } else {
                new Thread(new ThreadPractice3(100, semaphores[i], semaphores[0])).start();
            }
        }
    }

}
