package com.quyc.learn.javabasic.designpattern.stracture.facade;

/**
 * 最少知识原则：只和你的密友谈话。也就是说客户对象所需要交互的对象应当尽可能少。
 *
 * Created by quyuanchao on 2019/2/16 23:50.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.watchMovie();
    }
}