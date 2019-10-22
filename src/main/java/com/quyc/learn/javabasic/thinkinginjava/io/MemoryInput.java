package com.quyc.learn.javabasic.thinkinginjava.io;//: io/MemoryInput.java

import java.io.IOException;
import java.io.StringReader;

public class MemoryInput {
    public static void main(String[] args)
            throws IOException {
        StringReader in = new StringReader(
                BufferedInputFile.read("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\thinkinginjava\\io\\MemoryInput.java"));
        int c;
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }
    }
} /* (Execute to see output) *///:~
