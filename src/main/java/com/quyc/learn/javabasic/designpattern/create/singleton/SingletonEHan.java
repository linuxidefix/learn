package com.quyc.learn.javabasic.designpattern.create.singleton;

/**
 * e.g. java.lang.Runtime
 * 饿汉式-线程安全
 *
 * 线程不安全问题主要是由于 uniqueInstance 被实例化多次，采取直接实例化 uniqueInstance 的方式就不会产生线程不安全问题。
 *
 * 但是直接实例化的方式也丢失了延迟实例化带来的节约资源的好处
 *
 * Created by quyuanchao on 2019/2/16 17:45.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class SingletonEHan {

    private static SingletonEHan uniqueInstance = new SingletonEHan();

    private SingletonEHan() {
    }

    public static SingletonEHan getUniqueInstance() {
        return uniqueInstance;
    }
}
