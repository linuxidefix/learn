package com.quyc.learn.javabasic.designpattern.action.visitor;

/**
 * Created by quyuanchao on 2019/2/16 23:18.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface Element {
    void accept(Visitor visitor);
}
