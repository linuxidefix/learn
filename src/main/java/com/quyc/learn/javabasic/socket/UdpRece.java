package com.quyc.learn.javabasic.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by quyuanchao on 2019-2-14 18:16.
 * <p>Title: com.zjgf.service.gold</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class UdpRece {
    public static void main(String[] args) throws Exception {
//		1，创建udp的socket服务。
        DatagramSocket ds = new DatagramSocket(10000);
//		2，定义数据包，用于存储接收到数据。先定义字节数组，数据包会把数据存储到字节数组中。
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf,buf.length);
//		3，通过socket服务的接收方法将收到的数据存储到数据包中。
        ds.receive(dp);//该方法是阻塞式方法。
//		4，通过数据包的方法获取数据包中的具体数据内容，比如ip，端口，数据等等。
        String ip = dp.getAddress().getHostAddress();
        int port = dp.getPort();
        String text = new String(dp.getData(),0,dp.getLength());//将字节数组中的有效部分转成字符串。
        System.out.println(ip+":"+port+"--"+text);
//		5，关闭资源。
        ds.close();
    }

}
