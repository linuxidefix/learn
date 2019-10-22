package com.quyc.learn.javabasic.designpattern.create.singleton;

/**
 * 懒汉式-线程安全
 *
 * 只需要对 getUniqueInstance() 方法加锁，那么在一个时间点只能有一个线程能够进入该方法，从而避免了实例化多次 uniqueInstance。
 * 但是当一个线程进入该方法之后，其它试图进入该方法的线程都必须等待，即使 uniqueInstance 已经被实例化了。这会让线程阻塞时间过长，因此该方法有性能问题，不推荐使用。
 *
 * Created by quyuanchao on 2019/2/16 17:45.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class SingletonLanHanAnQuan {

    private static SingletonLanHanAnQuan uniqueInstance;

    private SingletonLanHanAnQuan() {
    }

    public static synchronized SingletonLanHanAnQuan getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SingletonLanHanAnQuan();
        }
        return uniqueInstance;
    }
}
