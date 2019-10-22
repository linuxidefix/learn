package com.quyc.learn.javabasic.thread.practice;

import java.util.LinkedList;

/**
 * Created by quyuanchao on 2019/3/17 20:41.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MyBlockingQueuePractice<T> {

    private LinkedList<T> list = new LinkedList<T>();
    private int limit = 10;

    public MyBlockingQueuePractice(int limit) {
        this.limit = limit;
    }

    public synchronized boolean enqueue(T t) {
        while (list.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        notifyAll();
        return true;
    }

    public synchronized T dequeue() {
        while (list.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        return list.removeFirst();
    }

}
