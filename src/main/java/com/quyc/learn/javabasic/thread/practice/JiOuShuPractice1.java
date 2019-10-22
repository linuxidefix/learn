package com.quyc.learn.javabasic.thread.practice;

/**
 * Created by quyuanchao on 2019/3/17 20:36.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class JiOuShuPractice1 implements Runnable {
    private static int num;

    public static void main(String[] args) {
        JiOuShuPractice1 jiOuShuPractice1 = new JiOuShuPractice1();
        new Thread(jiOuShuPractice1).start();
        new Thread(jiOuShuPractice1).start();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                notify();
                System.out.println(Thread.currentThread().getName() + ": " + num++);
                if (num>100) {
                    System.exit(0);
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
