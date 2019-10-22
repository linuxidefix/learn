package com.quyc.learn.javabasic.Generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by quyuanchao on 2019-2-18 17:17.
 * <p>Title: com.review</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());
    static class Reader<T> {
        T readExact(List<? extends T> list) {
            return list.get(0);
        }
    }
    static void f1() {
        Reader<Fruit> fruitReader = new Reader<Fruit>();
        // Errors: List<Fruit> cannot be applied to List<Apple>.
         Fruit f = fruitReader.readExact(apples);
    }
    public static void main(String[] args) {
//        f1();
        // Wildcards allow covariance:
        List<? extends Fruit> flist = new ArrayList<Apple>();
        // Compile Error: can't add any type of object:
//        flist.add(new Apple());
        // flist.add(new Orange())
        // flist.add(new Fruit())
        // flist.add(new Object())
        flist.add(null); // Legal but uninteresting
        // We Know that it returns at least Fruit:
        Fruit f = flist.get(0);
    }
}
