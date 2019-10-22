package com.quyc.learn.javabasic.thinkinginjava.container;

import java.util.ArrayList;

/**
 * 自定义 Stack 类
 *
 * @author quyuanchao
 * @date 2018-6-26 23:22
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Stack<T> {

    //    private LinkedList<T> storge = new LinkedList<>();
    private ArrayList<T> storge = new ArrayList<>();

    public void push(T v) {
        storge.add(v);
//        storge.addFirst(v);
    }

    public T peek() {
        return storge.get(storge.size() - 1);
//        return storge.getFirst();
    }

    public T pop() {
        return storge.remove(storge.size());
//        return storge.removeFirst();
    }

    public boolean empty() {
        return storge.isEmpty();
    }

    @Override
    public String toString() {
        return storge.toString();
    }

}
