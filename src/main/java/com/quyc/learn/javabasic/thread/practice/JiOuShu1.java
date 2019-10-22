package com.quyc.learn.javabasic.thread.practice;

/**
 * 2 线程交替打印 奇偶数
 * Created by quyuanchao on 2019/3/15 21:46.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class JiOuShu1 {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            int i = 1;

            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (i <= 100) {
                            notifyAll();
                            try {
                                System.out.println(Thread.currentThread().getName() + ": " + i);
                                i++;
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
    }


}

