package com.quyc.learn.javabasic.thinkinginjava.container;

import java.util.Stack;

/**
 * Stack 测试
 * @author quyuanchao
 * @date 2018-6-10 22:02
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class StackTest {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        String s = "this is my first time know Stack";
        String[] strings = s.split(" ");
        for (String string : strings) {
            stack.push(string);
        }
        System.out.println("stack = " + stack);
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
    }

}



