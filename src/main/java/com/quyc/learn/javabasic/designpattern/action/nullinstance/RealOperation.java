package com.quyc.learn.javabasic.designpattern.action.nullinstance;

/**
 * Created by quyuanchao on 2019/2/16 23:27.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class RealOperation extends AbstractOperation {
    @Override
    void request() {
        System.out.println("do something");
    }
}
