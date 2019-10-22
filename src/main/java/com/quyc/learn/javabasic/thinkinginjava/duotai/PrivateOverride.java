package com.quyc.learn.javabasic.thinkinginjava.duotai;

/**
 * Created by quyuanchao on 2018-4-21 23:05.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class PrivateOverride {

    private void f() {
        System.out.println("private f()");
    }

    public void m() {
        System.out.println("public PrivateOverride.m()");
    }

    /**
     * 只有非 private 的方法才能被子类覆盖，private修饰的方法默认是被final修饰的，是对子类屏蔽的。
     * @param args
     */
    public static void main(String[] args) {
        PrivateOverride po = new Derived();
        po.f();
        po.m();
        // po.w();
        ((Derived) po).w();
        Derived derived = new Derived();
        derived.f();
        derived.m();
        derived.w();
    }
}

class Derived extends PrivateOverride {
    public void f() {
        System.out.println("public f()");
    }

    @Override
    public void m() {
        System.out.println("public Derived.m()");
    }

    public void w() {
        System.out.println("public Derived.m()");
    }
}
