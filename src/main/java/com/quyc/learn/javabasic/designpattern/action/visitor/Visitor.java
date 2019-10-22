package com.quyc.learn.javabasic.designpattern.action.visitor;

/**
 * Created by quyuanchao on 2019/2/16 23:19.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface Visitor {
    void visit(Customer customer);

    void visit(Order order);

    void visit(Item item);
}
