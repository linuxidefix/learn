package com.quyc.learn.javabasic.string;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by quyuanchao on 2019-2-18 15:42.
 * <p>Title: com.review</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class StringTest {

    public static void main(String[] args) {
//        intern();
        splitDemo();
    }

    private static void intern() {
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

    private static void splitDemo() {
        String param = "sss_aaa__vvv";
        String split = "__";
        for (String s : param.split(split)) {
            System.out.println(s);
        }
        String param2 = "ab-!-cd--!-ef";
        String split2 = "-!-";
        for (String s : param2.split(split2)) {
            System.out.println(s);
        }
        System.out.println("==========");
        for (String s : StringUtils.split(param, split)) {
            System.out.println(s);
        }
        for (String s : StringUtils.split(param2, split2)) {
            System.out.println(s);
        }
        System.out.println("==========");
        for (String s : StringUtils.splitByWholeSeparator(param, split)) {
            System.out.println(s);
        }
        for (String s : StringUtils.splitByWholeSeparator(param2, split2)) {
            System.out.println(s);
        }
    }
}
