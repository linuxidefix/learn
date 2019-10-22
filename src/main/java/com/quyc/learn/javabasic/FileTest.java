package com.quyc.learn.javabasic;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by quyuanchao on 2019/2/15 22:21.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class FileTest {
    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            System.out.println(dir.getName());
            return;
        }
        for (File file : dir.listFiles()) {
            listAllFiles(file);
        }
    }

    public static void copyFile(String src, String dist) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(src);
        FileOutputStream fileOutputStream = new FileOutputStream(dist);

        byte[] buffer = new byte[1 * 1024];
        int cnt = 0;
        while ((cnt = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
            fileOutputStream.write(buffer, 0, cnt);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static void readFileContent(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filePath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("line = " + line);
        }
        bufferedReader.close();

    }

    public static void fastCopy(String src, String dist) throws IOException {

        /* 获得源文件的输入字节流 */
        FileInputStream fin = new FileInputStream(src);

        /* 获取输入字节流的文件通道 */
        FileChannel fcin = fin.getChannel();

        /* 获取目标文件的输出字节流 */
        FileOutputStream fout = new FileOutputStream(dist);

        /* 获取输出字节流的文件通道 */
        FileChannel fcout = fout.getChannel();

        /* 为缓冲区分配 1024 个字节 */
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {

            /* 从输入通道中读取数据到缓冲区中 */
            int r = fcin.read(buffer);

            /* read() 返回 -1 表示 EOF */
            if (r == -1) {
                break;
            }

            /* 切换读写 */
            buffer.flip();

            /* 把缓冲区的内容写入输出文件中 */
            fcout.write(buffer);

            /* 清空缓冲区 */
            buffer.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        listAllFiles(new File("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com"));
//        copyFile("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\review\\FileTest.java",
//                "D:\\copy.java");
        readFileContent("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\review\\FileTest.java");
        fastCopy("D:\\computer\\MyWorkSpaces\\IDEAProjects\\SpringLearn\\src\\com\\review\\FileTest.java",
                "D:\\copy.java");
    }
}
