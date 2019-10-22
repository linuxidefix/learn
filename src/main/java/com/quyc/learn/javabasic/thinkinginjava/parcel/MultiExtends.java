package com.quyc.learn.javabasic.thinkinginjava.parcel;

/**
 * 使用内部类实现多继承
 * Created by quyuanchao on 2018-4-24 0:20.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MultiExtends {
    private static void takesD(D d) {

    }

    private static void takesE(E e) {

    }

    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}

class D {

}

abstract class E {

}

class Z extends D {
    E makeE() {
        return new E() {
        };
    }
}
