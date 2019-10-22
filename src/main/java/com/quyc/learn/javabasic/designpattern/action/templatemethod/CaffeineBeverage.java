package com.quyc.learn.javabasic.designpattern.action.templatemethod;

/**
 * Created by quyuanchao on 2019/2/16 22:59.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public abstract class CaffeineBeverage {

    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("boilWater");
    }

    void pourInCup() {
        System.out.println("pourInCup");
    }
}
