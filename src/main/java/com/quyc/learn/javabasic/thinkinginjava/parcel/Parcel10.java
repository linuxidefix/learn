package com.quyc.learn.javabasic.thinkinginjava.parcel;

/**
 * 匿名内部类的实例化
 * Created by quyuanchao on 2018-4-23 23:25.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Parcel10 {
    public Destination destination(final String dest, final float price) {
        return new Destination(dest) {
            private int cost;
            // 字段初始化
            {
                cost = Math.round(price);
                if (cost > 100) {
                    System.out.println("Over budget");
                }
            }

            private String label = dest;

            @Override
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel10 parcel10 = new Parcel10();
        Destination china = parcel10.destination("China", 101.23f);
        System.out.println(china.readLabel());
    }
}

class Destination {
    private String label;

    Destination(String whereTo) {
        label = whereTo;
    }

    String readLabel() {
        return label;
    }
}
