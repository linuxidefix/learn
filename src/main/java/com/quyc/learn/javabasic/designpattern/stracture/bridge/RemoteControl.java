package com.quyc.learn.javabasic.designpattern.stracture.bridge;

/**
 * Created by quyuanchao on 2019/2/16 23:34.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public abstract class RemoteControl {
    protected TV tv;

    public RemoteControl(TV tv) {
        this.tv = tv;
    }

    public abstract void on();

    public abstract void off();

    public abstract void tuneChannel();
}
