package com.quyc.learn.javabasic.designpattern.action.observer;


/**
 * Created by quyuanchao on 2019/2/16 22:40.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
