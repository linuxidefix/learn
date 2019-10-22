package com.quyc.learn.javabasic.designpattern.action.interator;

/**
 * e.g. java.util.Iterator
 * Created by quyuanchao on 2019/2/16 22:26.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {

    public static void main(String[] args) {
        Aggregate aggregate = new ConcreteAggregate();
        Iterator<Integer> iterator = aggregate.createIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
