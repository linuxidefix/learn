package com.quyc.learn.javabasic.designpattern.action.visitor;

/**
 * Created by quyuanchao on 2019/2/16 23:19.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Item implements Element {

    private String name;

    Item(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
