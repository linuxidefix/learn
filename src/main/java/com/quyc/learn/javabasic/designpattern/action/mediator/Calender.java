package com.quyc.learn.javabasic.designpattern.action.mediator;

/**
 * Created by quyuanchao on 2019/2/16 22:30.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Calender extends Colleague {
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("calender");
    }

    public void doCalender() {
        System.out.println("doCalender()");
    }
}
