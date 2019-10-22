package com.quyc.learn.javabasic.thread.practice;

/**
 * 2 线程交替打印 奇偶数
 * Created by quyuanchao on 2019/3/15 23:18.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class JiOuShu implements Runnable {

    public static int num;


    @Override
    public void run() {

        while (true) {
            synchronized (JiOuShu.class) {
                JiOuShu.class.notify();
                if (num > 100) {
                    System.exit(0);
                }
                System.out.println(Thread.currentThread().getName() + ": " + num++);
                try {
                    JiOuShu.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new JiOuShu());
        Thread thread2 = new Thread(new JiOuShu());
        thread1.start();
        thread2.start();
    }
}
