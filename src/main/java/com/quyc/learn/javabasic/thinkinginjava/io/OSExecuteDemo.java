package com.quyc.learn.javabasic.thinkinginjava.io;//: io/OSExecuteDemo.java
// Demonstrates standard I/O redirection.

import com.quyc.learn.javabasic.thinkinginjava.net.mindview.util.OSExecute;

public class OSExecuteDemo {
    public static void main(String[] args) {
        OSExecute.command("javap D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\thinkinginjava\\io\\OSExecuteDemo");
    }
} /* Output:
Compiled from "OSExecuteDemo.java"
public class OSExecuteDemo extends java.lang.Object{
    public OSExecuteDemo();
    public static void main(java.lang.String[]);
}
*///:~
