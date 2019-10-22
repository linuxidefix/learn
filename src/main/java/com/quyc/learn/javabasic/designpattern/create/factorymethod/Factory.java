package com.quyc.learn.javabasic.designpattern.create.factorymethod;


import com.quyc.learn.javabasic.designpattern.create.simplefactory.Product;

/**
 * e.g. java.util.Calendar
 * Created by quyuanchao on 2019/2/16 18:04.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public abstract class Factory {
    abstract public Product factoryMethod();
    public void doSomething() {
        Product product = factoryMethod();
        // do something with the product
    }
}
