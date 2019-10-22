package com.quyc.learn.javabasic.socket;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by quyuanchao on 2019-2-14 18:20.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class TcpClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 10002);
        OutputStream out = s.getOutputStream();//获取了socket流中的输出流对象。
        out.write("tcp演示，哥们又来了!".getBytes());
        s.close();
    }
}
