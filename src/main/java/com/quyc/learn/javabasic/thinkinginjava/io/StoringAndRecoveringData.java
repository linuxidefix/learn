package com.quyc.learn.javabasic.thinkinginjava.io;

import java.io.*;

/**
 *
 * @author quyuanchao
 * @date 2018/9/9 15:45
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class StoringAndRecoveringData {

    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\thinkinginjava\\io\\Data.txt")));
        out.writeDouble(3.1415);
        out.writeUTF("this is PI");
        out.writeDouble(1.41413);
        out.writeUTF("square root of 2");
        out.close();
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\thinkinginjava\\io\\Data.txt")));
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
        in.close();
    }

}