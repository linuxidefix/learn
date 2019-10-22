package com.quyc.learn.javabasic.designpattern.action.interator;

/**
 * Created by quyuanchao on 2019/2/16 22:24.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface Aggregate {
    Iterator createIterator();
}
