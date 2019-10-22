package com.quyc.learn.javabasic.thinkinginjava.cuncurrency;//: concurrency/CaptureUncaughtException.java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 运行结果与书上的不一致
 */
class ExceptionThread2 implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println(
                "eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements
        Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(
                new MyUncaughtExceptionHandler());
        System.out.println(
                "eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(
                new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }
} /* Output: (90% match)
HandlerThreadFactory@de6ced creating new Thread
created Thread[Thread-0,5,main]
eh = MyUncaughtExceptionHandler@1fb8ee3
run() by Thread[Thread-0,5,main]
eh = MyUncaughtExceptionHandler@1fb8ee3
caught java.lang.RuntimeException
*///:~
/*com.thinkinginjava.curncurrency.HandlerThreadFactory@5197848c creating new Thread
created Thread[Thread-0,5,main]
eh = com.thinkinginjava.curncurrency.MyUncaughtExceptionHandler@17f052a3
run() by Thread[Thread-0,5,main]
eh = com.thinkinginjava.curncurrency.MyUncaughtExceptionHandler@17f052a3
com.thinkinginjava.curncurrency.HandlerThreadFactory@5197848c creating new Thread
created Thread[Thread-1,5,main]
eh = com.thinkinginjava.curncurrency.MyUncaughtExceptionHandler@6628122c
caught java.lang.RuntimeException*/
