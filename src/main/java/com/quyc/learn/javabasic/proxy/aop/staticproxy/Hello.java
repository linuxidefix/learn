package com.quyc.learn.javabasic.proxy.aop.staticproxy;

import java.util.Random;

/**
 * 目标对象，被代理对象
 * Created by quyuanchao on 2019/3/16 16:20.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Hello implements IHello {

    @Override
    public void sayHello(String string) {
        System.out.println("hello " + string);
        try {
            Random random = new Random();
            int i = random.nextInt(100);
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
