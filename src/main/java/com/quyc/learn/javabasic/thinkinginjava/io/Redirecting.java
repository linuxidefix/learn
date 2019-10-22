package com.quyc.learn.javabasic.thinkinginjava.io;//: io/Redirecting.java
// Demonstrates standard I/O redirection.

import java.io.*;

public class Redirecting {
    public static void main(String[] args)
            throws IOException {
        PrintStream console = System.out;
        BufferedInputStream in = new BufferedInputStream(
                new FileInputStream("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\thinkinginjava\\io\\Redirecting.java"));
        PrintStream out = new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\thinkinginjava\\io\\test.out")));
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        out.close(); // Remember this!
        System.setOut(console);
    }
} ///:~
