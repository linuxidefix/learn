package com.quyc.learn.javabasic.thread.practice;

/**
 * Created by quyuanchao on 2019/3/17 16:01.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadPractice implements Runnable {
    volatile int b = 100;

    synchronized void m1() throws InterruptedException {
        b = 1000;
        Thread.sleep(500);
        System.out.println("b=" + b);
    }

    synchronized void m2() throws InterruptedException {
        Thread.sleep(250);
        b = 2000;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPractice tt = new ThreadPractice();
        Thread t = new Thread(tt);
        t.start(); //2

        tt.m2(); //3
        System.out.println("main thread b=" + tt.b);
    }

    @Override
    public void run() {
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
