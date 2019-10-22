package com.quyc.learn.javabasic.socket;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by quyuanchao on 2019-2-14 18:21.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(10002);//建立服务端的socket服务
        Socket s = ss.accept();//获取客户端对象
        String ip = s.getInetAddress().getHostAddress();
        System.out.println(ip+".....connected");
//		可以通过获取到的socket对象中的socket流和具体的客户端进行通讯。
        InputStream in = s.getInputStream();//读取客户端的数据，使用客户端对象的socket读取流
        byte[] buf = new byte[1024];
        int len = in.read(buf);
        String text = new String(buf,0,len);
        System.out.println(text);
//		如果通讯结束，关闭资源。注意：要先关客户端，在关服务端。
        s.close();
        ss.close();
    }

}
