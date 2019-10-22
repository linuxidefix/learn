package com.quyc.learn.javabasic.designpattern.action.command;

/**
 * Created by quyuanchao on 2019/2/16 22:02.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}
