package com.quyc.learn.javabasic.designpattern.create.singleton;

/**
 * 懒汉式-线程不安全
 *
 * 这个实现在多线程环境下是不安全的，如果多个线程能够同时进入 if (uniqueInstance == nullinstance) ，并且此时 uniqueInstance 为 nullinstance，
 * 那么会有多个线程执行 uniqueInstance = new Singleton(); 语句，这将导致实例化多次 uniqueInstance。
 *
 * Created by quyuanchao on 2019/2/16 17:45.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class SingletonLanHan {

    private static SingletonLanHan uniqueInstance;

    private SingletonLanHan() {
    }

    public static SingletonLanHan getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SingletonLanHan();
        }
        return uniqueInstance;
    }
}
