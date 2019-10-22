package com.quyc.learn.javabasic.designpattern.stracture.facade;

/**
 * Created by quyuanchao on 2019/2/16 23:50.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Facade {
    private SubSystem subSystem = new SubSystem();

    public void watchMovie() {
        subSystem.turnOnTV();
        subSystem.setCD("a movie");
        subSystem.startWatching();
    }
}
