package com.quyc.learn.javabasic.Algorithm.stack;

/**
 * Created by quyuanchao on 2019-2-25 16:19.
 * <p>Title: com.review.Algorithm.stack</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface MyStack<Item> extends Iterable<Item> {

    MyStack<Item> push(Item item);

    Item pop() throws Exception;

    boolean isEmpty();

    int size();

}