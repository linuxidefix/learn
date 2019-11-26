package com.quyc.learn.javabasic;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.util.NamedThreadFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: andy
 * @create: 2019/11/25 19:32
 * @description:
 */
@Slf4j
@RestController
public class ThreadDemo {


    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new NamedThreadFactory("concurrent-%d"));

    @RequestMapping("startTask")
    public String startTask() throws InterruptedException {
        log.info("task start");
        for (int i = 0; i < 10; i++) {
            executor.execute(new ThreadMain(i));
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
        }
        executor.shutdown();
        log.info("task end");
        return "success";
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executor.execute(new ThreadMain(i));
        }
        try {
            TimeUnit.SECONDS.sleep(60);
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ThreadMain implements Runnable {
    private int id;

    public ThreadMain(int id) {
        this.id = id;
    }

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder()
            .setNameFormat("schedule-%d")
            .build());

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " begin");
        AtomicInteger count = new AtomicInteger();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("threadMain:" + id + " " + "count: " + count.getAndIncrement());
        }, 10, 10, TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(35);
            scheduledExecutorService.shutdown();
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}