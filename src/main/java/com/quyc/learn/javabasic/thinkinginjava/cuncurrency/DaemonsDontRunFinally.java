package com.quyc.learn.javabasic.thinkinginjava.cuncurrency;//: concurrency/DaemonsDontRunFinally.java
// Daemon threads don't run the finally clause

import java.util.concurrent.TimeUnit;


class ADaemon implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        } finally {
            // 若该线程是后台线程，则主线程死亡后，该线程也死亡，且不执行finally中的代码。
            System.out.println("This should always run?");
        }
    }
}

public class DaemonsDontRunFinally {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
    }
} /* Output:
Starting ADaemon
*///:~
