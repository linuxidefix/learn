package com.quyc.learn.javabasic.proxy.aop.dynamicproxy2;

/**
 * 该类为切面，横切关注点的抽象
 * Created by quyuanchao on 2019/3/16 16:47.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface ILogger {

    /**
     * 通知：在连接点之前或者之后执行的具体方法，通知分为前置，后置，异常，最终，环绕
     */
    void start();

    /**
     * 通知：在连接点之前或者之后执行的具体方法，通知分为前置，后置，异常，最终，环绕
     */
    void end();

}
