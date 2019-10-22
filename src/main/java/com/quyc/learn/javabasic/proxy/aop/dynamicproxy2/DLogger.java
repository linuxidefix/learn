package com.quyc.learn.javabasic.proxy.aop.dynamicproxy2;

/**
 * 该类为切面的具体实现
 * Created by quyuanchao on 2019/3/16 16:50.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class DLogger implements ILogger {

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    public void start() {
        long start = System.currentTimeMillis();
        threadLocal.set(start);
        System.out.println(Thread.currentThread().getName() + " " + start + " before");
    }

    @Override
    public void end() {
        long end = System.currentTimeMillis();
        Long start = threadLocal.get();
        System.out.println(Thread.currentThread().getName() + " " + end + " after, total = " + (end - start));
    }
}
