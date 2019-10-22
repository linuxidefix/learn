package com.quyc.learn.javabasic.designpattern.stracture.adaptor;

/**
 * Created by quyuanchao on 2019/2/16 23:31.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class WildTurkey implements Turkey {
    @Override
    public void gobble() {
        System.out.println("gobble!");
    }
}
