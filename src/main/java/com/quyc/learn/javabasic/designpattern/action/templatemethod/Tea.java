package com.quyc.learn.javabasic.designpattern.action.templatemethod;

/**
 * Created by quyuanchao on 2019/2/16 22:59.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Tea extends CaffeineBeverage {
    @Override
    void brew() {
        System.out.println("Tea.brew");
    }

    @Override
    void addCondiments() {
        System.out.println("Tea.addCondiments");
    }
}
