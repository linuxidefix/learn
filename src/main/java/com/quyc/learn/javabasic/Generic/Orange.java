package com.quyc.learn.javabasic.Generic;

/**
 * Created by quyuanchao on 2019-2-18 17:17.
 * <p>Title: com.review</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Orange extends Fruit {
    private String name;
    public Orange(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
