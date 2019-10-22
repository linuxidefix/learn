package com.quyc.learn.javabasic.designpattern.stracture.composite;

/**
 * Created by quyuanchao on 2019/2/16 23:38.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public abstract class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public void print() {
        print(0);
    }

    abstract void print(int level);

    abstract public void add(Component component);

    abstract public void remove(Component component);
}
