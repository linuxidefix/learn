package com.quyc.learn.javabasic.thinkinginjava.array;

import java.util.Arrays;

/**
 * 测试数组
 * @author quyuanchao
 * @date 2018/9/2 16:00
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ArrayTest {

    public static void main(String[] args) {

        Integer[] a = new Integer[5];
        for (Integer integer : a) {
            System.out.println("integer = " + integer);
        }
        System.out.println("a.length = " + a.length);

        Integer[][] a1 = {
                {1, 2, 3}, {4, 5, 6}
        };
        double[][][] a2 = {
                {{1.1, 2.2}, {3.3, 4.4}},
                {{5.5,6.6}, {7.7,8.8}},
                {{9.9,1.2}, {2.3,3.4}},
        };

        String[][] a3 = {
                {"a","b"},{"c","d"},{"e","f"}
        };

        System.out.println("a1 = " + Arrays.deepToString(a1));
        System.out.println("a2 = " + Arrays.deepToString(a2));
        System.out.println("a3 = " + Arrays.deepToString(a3));

    }

}
