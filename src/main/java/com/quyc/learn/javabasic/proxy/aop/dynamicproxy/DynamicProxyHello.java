package com.quyc.learn.javabasic.proxy.aop.dynamicproxy;

import com.quyc.learn.javabasic.proxy.aop.staticproxy.Hello;
import com.quyc.learn.javabasic.proxy.aop.staticproxy.IHello;
import com.quyc.learn.javabasic.proxy.aop.staticproxy.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理实现 aop
 * Created by quyuanchao on 2019/3/16 16:36.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class DynamicProxyHello implements InvocationHandler {

    private Object target;

    private Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Logger.start();
        result = method.invoke(target, args);
        Logger.end();
        return result;
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new DynamicProxyHello().bind(new Hello());
        hello.sayHello("world");
    }

}
