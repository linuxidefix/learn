package com.quyc.learn.javabasic.proxy.aop.staticproxy;

/**
 * Created by quyuanchao on 2019/3/16 16:20.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface IHello {

    /**
     * 这个方法是连接点，spring 只支持方法类型的连接点，连接点可以有许多个，切入点则是我们想要具体进行增强的一个或几个连接点
     * @param string
     */
    void sayHello(String string);
}
