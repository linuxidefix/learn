package com.quyc.learn.javabasic.designpattern.create.abstractfactory;

/**
 * Created by quyuanchao on 2019/2/16 18:15.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ConcreteFactory1 extends AbstractFactory {
    @Override
    AbstractProductA createProductA() {
        return new ProductA1();
    }

    @Override
    AbstractProductB createProductB() {
        return new ProductB1();
    }
}
