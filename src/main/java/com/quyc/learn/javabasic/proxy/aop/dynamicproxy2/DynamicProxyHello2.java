package com.quyc.learn.javabasic.proxy.aop.dynamicproxy2;

import com.quyc.learn.javabasic.proxy.aop.staticproxy.Hello;
import com.quyc.learn.javabasic.proxy.aop.staticproxy.IHello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 动态代理实现 增强方法与目标对象解耦的 aop
 *
 * 引入：在不修改代码的前提下，引入可以在运行期为类动态的添加一些字段或方法
 *
 * Created by quyuanchao on 2019/3/16 16:51.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class DynamicProxyHello2 implements InvocationHandler {

    private Object target;
    private Object proxy;

    /**
     * 织入，将切面应用到目标对象，并导致代理对象创建的过程.
     *
     * @param target the target
     * @param proxy  the proxy
     * @return the object
     */
    public Object bind(Object target, Object proxy) {
        this.target = target;
        this.proxy = proxy;
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method start = this.proxy.getClass().getDeclaredMethod("start", null);
        start.invoke(this.proxy, null);
        Object result = method.invoke(target, args);
        Method end = this.proxy.getClass().getDeclaredMethod("end", null);
        end.invoke(this.proxy, null);
        return result;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    IHello hello = (IHello) new DynamicProxyHello2().bind(new Hello(), new DLogger());
                    hello.sayHello("world");
                }
            });
        }
    }
}
