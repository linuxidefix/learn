package com.quyc.learn.javabasic.Generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quyuanchao on 2019-2-18 17:20.
 * <p>Title: com.review</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class GenericWriting {
    static List<Apple> apples = new ArrayList<Apple>();
    static List<Fruit> fruit = new ArrayList<Fruit>();
    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }
    static void f1() {
        writeExact(apples, new Apple());
        writeExact(fruit, new Apple());
    }
    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }
    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Apple());
    }
    public static void main(String[] args) {
        f1(); f2();
    }
}
