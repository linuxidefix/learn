package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.Semaphore;

/**
 * Created by quyuanchao on 2019/3/17 16:26.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadPractice1 implements Runnable {

    private char str;
    private int count;
    private int max;
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;

    public ThreadPractice1(char str, int max, Semaphore mySemaphore, Semaphore nextSemaphore) {
        this.str = str;
        this.max = max;
        this.mySemaphore = mySemaphore;
        this.nextSemaphore = nextSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mySemaphore.acquire();
                if (count > max) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " = " + str);
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                nextSemaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore[] semaphores = new Semaphore[3];
        for (int i = 0; i < 3; i++) {
            semaphores[i] = new Semaphore(1);
            if (i != 0) {
                semaphores[i].acquire();
            }
        }
        ThreadPractice1 practice1 = new ThreadPractice1('A', 10, semaphores[0], semaphores[1]);
        ThreadPractice1 practice2 = new ThreadPractice1('B', 10, semaphores[1], semaphores[2]);
        ThreadPractice1 practice3 = new ThreadPractice1('C', 10, semaphores[2], semaphores[0]);
        new Thread(practice1).start();
        new Thread(practice2).start();
        new Thread(practice3).start();
    }
}
