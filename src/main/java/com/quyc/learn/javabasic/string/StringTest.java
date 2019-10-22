package com.quyc.learn.javabasic.string;

/**
 * Created by quyuanchao on 2019-2-18 15:42.
 * <p>Title: com.review</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class StringTest {

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        String s3 = "abc";
        String s4 = s2.intern();
        String s5 = s3.intern();
        System.out.println(s1 == s2);
        System.out.println(s2 == s3);
        System.out.println(s2 == s4);
        System.out.println(s4 == s5);
    }
}
