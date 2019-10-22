package com.quyc.learn.javabasic.thread.practice;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by quyuanchao on 2019/3/17 14:02.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MyBlockingQueue<T> {

    private LinkedList<T> queue = new LinkedList<>();
    private int limit = 10;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public MyBlockingQueue(Integer limit) {
        this.limit = limit;
    }

    public boolean enqueue(T t) {
        try {
            lock.lock();
            while (queue.size() == limit) {
                condition.await();
            }
            queue.add(t);
            condition.signalAll();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public T dequeue() {
        try {
            lock.lock();
            while (queue.size() == 0) {
                condition.await();
            }
            condition.signalAll();
            return queue.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < 5; i++) {
            linkedList.add(i);
        }
        System.out.println("linkedList = " + linkedList);
        for (int i = 0; i < 5; i++) {
            System.out.println("linkedList.removeFirst() = " + linkedList.removeFirst());
        }

    }

}
