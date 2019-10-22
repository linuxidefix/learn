package com.quyc.learn.javabasic.designpattern.action.strategy;

/**
 * Created by quyuanchao on 2019/2/16 22:57.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Squeak implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("squeak!");
    }
}
