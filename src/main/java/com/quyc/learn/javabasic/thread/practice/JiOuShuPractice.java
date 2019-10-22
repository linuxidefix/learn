package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.Semaphore;

/**
 * Created by quyuanchao on 2019/3/17 20:16.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class JiOuShuPractice implements Runnable {

    private static int i;
    private static final int max = 100;
    private Semaphore myLock;
    private Semaphore nextlock;

    public JiOuShuPractice(Semaphore myLock, Semaphore nextlock) {
        this.myLock = myLock;
        this.nextlock = nextlock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                myLock.acquire();
                System.out.println(Thread.currentThread().getName() + ": " + i++);
                if (i > 100) {
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                nextlock.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore lockJi = new Semaphore(1);
        Semaphore lockOu = new Semaphore(1);
        lockJi.acquire();
        new Thread(new JiOuShuPractice(lockOu, lockJi)).start();
        new Thread(new JiOuShuPractice(lockJi, lockOu)).start();
    }
}
