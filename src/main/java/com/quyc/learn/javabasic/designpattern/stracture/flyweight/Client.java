package com.quyc.learn.javabasic.designpattern.stracture.flyweight;

/**
 * e.g. java.lang.Integer#valueOf(int)
 *      java.lang.Boolean#valueOf(boolean)
 *      java.lang.Byte#valueOf(byte)
 *      java.lang.Character#valueOf(char)
 *
 * Java 利用缓存来加速大量小对象的访问时间。
 *
 * Created by quyuanchao on 2019/2/16 23:53.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {

    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight flyweight1 = factory.getFlyweight("aa");
        Flyweight flyweight2 = factory.getFlyweight("aa");
        flyweight1.doOperation("x");
        flyweight2.doOperation("y");
    }
}