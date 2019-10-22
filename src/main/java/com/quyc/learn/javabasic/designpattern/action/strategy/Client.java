package com.quyc.learn.javabasic.designpattern.action.strategy;

/**
 * e.g. java.util.Comparator#compare()
 * Created by quyuanchao on 2019/2/16 22:57.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {

    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.setQuackBehavior(new Squeak());
        duck.performQuack();
        duck.setQuackBehavior(new Quack());
        duck.performQuack();
    }
}
