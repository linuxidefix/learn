package com.quyc.learn.javabasic.thinkinginjava.io;

import java.io.PrintWriter;

/**
 * Created by quyuanchao on 2018/9/9 16:37.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ChangeSystemOut {

    public static void main(String[] args) {
        PrintWriter writer = new PrintWriter(System.out, true);
        writer.println("hello world");
//        writer.flush();
    }
}
