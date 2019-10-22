package com.quyc.learn.javabasic.thinkinginjava.parcel;

/**
 * 使用内部类实现多接口实现
 * Created by quyuanchao on 2018-4-24 0:17.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MultiInterfaces {
    private static void takesA(A a) {

    }

    private static void takesB(B b) {

    }

    public static void main(String[] args) {
        X x = new X();
        Y y = new Y();
        takesA(x);
        takesA(y);
        takesB(x);
        takesB(y.makeB());
    }
}

interface A {

}

interface B {

}

class X implements A, B {
}

class Y implements A {
    B makeB() {
        return new B() {
        };
    }
}