package com.quyc.learn.javabasic.designpattern.action.mediator;

/**
 * e.g.
 * 1. All scheduleXXX() methods of java.util.Timer
 * 2. java.util.concurrent.Executor#execute()
 * 3. submit() and invokeXXX() methods of java.util.concurrent.ExecutorService
 * 4. scheduleXXX() methods of java.util.concurrent.ScheduledExecutorService
 * 5. java.lang.reflect.Method#invoke()
 *
 * Created by quyuanchao on 2019/2/16 22:31.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {
    public static void main(String[] args) {
        Alarm alarm = new Alarm();
        CoffeePot coffeePot = new CoffeePot();
        Calender calender = new Calender();
        Sprinkler sprinkler = new Sprinkler();
        Mediator mediator = new ConcreteMediator(alarm, coffeePot, calender, sprinkler);
        // 闹钟事件到达，调用中介者就可以操作相关对象
        alarm.onEvent(mediator);
    }
}
