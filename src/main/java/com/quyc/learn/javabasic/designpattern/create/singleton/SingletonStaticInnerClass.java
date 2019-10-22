package com.quyc.learn.javabasic.designpattern.create.singleton;

/**
 * 静态内部类实现
 *
 * 当 Singleton 类加载时，静态内部类 SingletonHolder 没有被加载进内存。
 * 只有当调用 getUniqueInstance() 方法从而触发 SingletonHolder.INSTANCE 时 SingletonHolder 才会被加载，
 * 此时初始化 INSTANCE 实例，并且 JVM 能确保 INSTANCE 只被实例化一次。
 *
 * 这种方式不仅具有延迟初始化的好处，而且由 JVM 提供了对线程安全的支持。
 *
 * Created by quyuanchao on 2019/2/16 17:51.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class SingletonStaticInnerClass {

    private SingletonStaticInnerClass() {

    }

    public static class SingletonInstanceHolder {
        private final static SingletonStaticInnerClass singletonStaticInnerClass = new SingletonStaticInnerClass();
    }

    public SingletonStaticInnerClass getUniqueInstance() {
        return SingletonInstanceHolder.singletonStaticInnerClass;
    }
}
