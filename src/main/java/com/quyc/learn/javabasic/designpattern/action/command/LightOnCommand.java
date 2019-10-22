package com.quyc.learn.javabasic.designpattern.action.command;

/**
 * Created by quyuanchao on 2019/2/16 22:01.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
