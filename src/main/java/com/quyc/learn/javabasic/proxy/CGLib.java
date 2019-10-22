package com.quyc.learn.javabasic.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by quyuanchao on 2019/3/7 1:58.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class CGLib {
    public static void main(String[] args){
        testProxy();
    }
    public static void testProxy() {
        //创建CGLIB增强类
        Enhancer en = new Enhancer();
        en.setSuperclass(Cat.class);
        en.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object target, Method method,
                                    Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before proxy");
                Object o = proxy.invokeSuper(target,args);
                System.out.println("after proxy");
                return o;
            }
        });
        Cat dogProxy = (Cat)en.create();
        dogProxy.eat();
    }
}

class Cat{
    public void eat() {
        System.out.println("eating");
    }
}
