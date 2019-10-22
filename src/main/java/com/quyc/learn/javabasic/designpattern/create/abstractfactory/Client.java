package com.quyc.learn.javabasic.designpattern.create.abstractfactory;

/**
 * e.g. javax.xml.transform.TransformerFactory
 * Created by quyuanchao on 2019/2/16 18:15.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {
    public static void main(String[] args) {
        AbstractFactory abstractFactory = new ConcreteFactory1();
        AbstractProductA productA = abstractFactory.createProductA();
        AbstractProductB productB = abstractFactory.createProductB();
        // do something with productA and productB
    }
}
