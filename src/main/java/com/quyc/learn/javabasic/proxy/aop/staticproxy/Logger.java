package com.quyc.learn.javabasic.proxy.aop.staticproxy;

/**
 * Created by quyuanchao on 2019/3/16 16:23.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Logger {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void start() {
        long start = System.currentTimeMillis();
        threadLocal.set(start);
        System.out.println(start + " before");
    }

    public static void end() {
        long end = System.currentTimeMillis();
        Long start = threadLocal.get();
        System.out.println(end + " after,total = " + (end - start));
    }

}
