package com.quyc.learn.javabasic.designpattern.stracture.decorator;

/**
 * e.g. java.io.BufferedInputStream(InputStream)
 *      java.io.DataInputStream(InputStream)
 *      java.io.BufferedOutputStream(OutputStream)
 *      java.util.zip.ZipOutputStream(OutputStream)
 *      java.util.Collections#checkedList|Map|Set|SortedSet|SortedMap
 *  类应该对扩展开放，对修改关闭：也就是添加新功能时不需要修改代码。饮料可以动态添加新的配料，而不需要去修改饮料的代码。
 * Created by quyuanchao on 2019/2/16 23:44.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {

    public static void main(String[] args) {
        Beverage beverage = new HouseBlend();
        beverage = new Mocha(beverage);
        beverage = new Milk(beverage);
        System.out.println(beverage.cost());
    }
}
