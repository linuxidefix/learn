package com.quyc.learn.javabasic.thread.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by quyuanchao on 2019/3/17 17:02.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreadPractice2 implements Runnable {

    private String string;
    private static int count;
    private int max;
    private int threadNo;
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public ThreadPractice2(String string, int max, int threadNo) {
        this.string = string;
        this.max = max;
        this.threadNo = threadNo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                if (count > max) {
                    break;
                }
                while (count % 3 != threadNo) {
                    condition.await();
                }
                System.out.println("thread " + threadNo + ": " + string);
                count++;
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ThreadPractice2("A", 30, 0)).start();
        new Thread(new ThreadPractice2("B", 30, 1)).start();
        new Thread(new ThreadPractice2("C", 30, 2)).start();
    }
}
