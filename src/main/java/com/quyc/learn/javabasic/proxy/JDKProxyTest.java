package com.quyc.learn.javabasic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by quyuanchao on 2019/3/6 22:19.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        Animal dog = (Animal) proxyFactory.createTargetProxy(new Dog());
        dog.eat();
    }
}

interface Animal {
    void eat();
}

class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("Dog eats boon!");
    }
}

class ProxyFactory implements InvocationHandler {
    private Object target;
    public Object createTargetProxy(Object object) {
        this.target = object;
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理开始");
        Object invoke = method.invoke(target, args);
        System.out.println("代理结束");
        return invoke;
    }
}
