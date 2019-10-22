package com.quyc.learn.javabasic.designpattern.action.observer;

/**
 * Created by quyuanchao on 2019/2/16 22:41.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface Observer {
    void update(float temp, float humidity, float pressure);
}
