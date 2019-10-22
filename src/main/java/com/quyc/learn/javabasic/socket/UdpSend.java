package com.quyc.learn.javabasic.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by quyuanchao on 2019-2-14 18:16.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class UdpSend {
    public static void main(String[] args) throws Exception {
//		1，建立udp的socket服务。
        DatagramSocket ds = new DatagramSocket(8888);//指定发送端口，不指定系统会随机分配。
//		2，明确要发送的具体数据。
        String text = "udp传输演示 哥们来了";
        byte[] buf = text.getBytes();
//		3，将数据封装成了数据包。
        DatagramPacket dp = new DatagramPacket(buf,
                buf.length, InetAddress.getByName("127.0.0.1"), 10000);
//		4，用socket服务的send方法将数据包发送出去。
        ds.send(dp);
//		5，关闭资源。
        ds.close();
    }
}