package com.quyc.learn.javabasic.thinkinginjava.parcel;

/**
 * 使用静态内部类经行测试（这样可以经测试代码封装在一个内部类中，发布的时候直接删除这个内部类的class文件）
 * Created by quyuanchao on 2018-4-23 23:58.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class TestBed {
    public void f() {
        System.out.println("f()");
    }

    public static class Tester {
        public static void main(String[] args) {
            TestBed testBed = new TestBed();
            testBed.f();
        }
    }

}
