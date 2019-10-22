package com.quyc.learn.javabasic.thread.practice;

import java.util.LinkedList;

/**
 * Created by quyuanchao on 2019/3/17 13:49.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MyStack {

    private LinkedList<Object> objects = new LinkedList<>();

    public Object peek() {
        return objects.getFirst();
    }

    public void push(Object object) {
        objects.addFirst(object);
    }

    public Object pop() {
        return objects.removeFirst();
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }

}
