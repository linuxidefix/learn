package com.quyc.learn.javabasic.thread;

/**
 * Created by quyuanchao on 2019-2-15 14:32.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class InterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread());
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
            }
        }
    }


}
