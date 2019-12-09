package com.quyc.learn.javabasic.stopwatch;

import com.google.common.base.Stopwatch;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

public class StopWatchDemo {

    public static void main(String[] args) throws InterruptedException {
//        springStopWatch();
//        apacheStopWatch();
        Stopwatch start = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("start.stop().toString() = " + start.stop().toString());
    }

    private static void apacheStopWatch() throws InterruptedException {
        org.apache.commons.lang3.time.StopWatch stopWatch = new org.apache.commons.lang3.time.StopWatch();
        stopWatch.start();
        TimeUnit.SECONDS.sleep(2);
        stopWatch.stop();
//        System.out.println("stopWatch.toSplitString() = " + stopWatch.toSplitString());
        System.out.println("stopWatch.toString() = " + stopWatch.toString());
    }

    private static void springStopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("task-stop-watch");
        stopWatch.start("task-1");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("stopWatch.prettyPrint() = " + stopWatch.prettyPrint());
        TimeUnit.SECONDS.sleep(2);
        stopWatch.stop();
        System.out.println("stopWatch.shortSummary() = " + stopWatch.shortSummary());
        System.out.println("stopWatch.prettyPrint() = " + stopWatch.prettyPrint());
    }

}
