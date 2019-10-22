package com.quyc.learn.javabasic.designpattern.stracture.decorator;

/**
 * Created by quyuanchao on 2019/2/16 23:44.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Mocha extends CondimentDecorator {

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return 1 + beverage.cost();
    }
}
