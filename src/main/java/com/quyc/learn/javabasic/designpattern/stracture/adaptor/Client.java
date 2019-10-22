package com.quyc.learn.javabasic.designpattern.stracture.adaptor;

/**
 * e.g. java.util.Arrays#asList()
 *      java.util.Collections#list()
 * Created by quyuanchao on 2019/2/16 23:31.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {
    public static void main(String[] args) {
        Turkey turkey = new WildTurkey();
        Duck duck = new TurkeyAdapter(turkey);
        duck.quack();
    }
}
