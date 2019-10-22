package com.quyc.learn.javabasic.thread.practice;

import java.util.LinkedList;

/**
 * Created by quyuanchao on 2019/3/17 20:53.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MyStackPractice<T> {

    private LinkedList<T> storage = new LinkedList<T>();

    public boolean put(T t) {
        return storage.add(t);
    }

    public T pop() {
        return storage.pop();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

}
