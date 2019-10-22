package com.quyc.learn.javabasic.designpattern.action.interator;

/**
 * Created by quyuanchao on 2019/2/16 22:25.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface Iterator<Item> {

    Item next();

    boolean hasNext();
}