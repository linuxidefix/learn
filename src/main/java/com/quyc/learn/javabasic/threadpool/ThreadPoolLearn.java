package com.quyc.learn.javabasic.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: andy
 * @create: 2019/7/17 14:24
 * @description:
 */
public class ThreadPoolLearn {

    private static ExecutorService executorService = Executors.newScheduledThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        executorService.execute(() -> System.out.println(" 执行任务 "));
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(2);
        executorService.execute(() -> System.out.println(" 再次执行任务 "));
    }


}
