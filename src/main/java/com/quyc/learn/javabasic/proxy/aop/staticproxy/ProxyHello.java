package com.quyc.learn.javabasic.proxy.aop.staticproxy;

/**
 * 静态代理实现 aop
 * Created by quyuanchao on 2019/3/16 16:21.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ProxyHello implements IHello {

    private Hello hello;

    public ProxyHello(Hello hello) {
        this.hello = hello;
    }

    @Override
    public void sayHello(String string) {
        Logger.start();
        hello.sayHello(string);
        Logger.end();
    }

    public static void main(String[] args) {
        IHello hello = new ProxyHello(new Hello());
        hello.sayHello("world");
    }

}
