package com.njcit.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author LiJun
 * @Date 2020/3/2 19:00
 */

public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true);

        String fileName = "G:\\Gepoint.rar";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long startTime = System.currentTimeMillis();
        //ʹ���㿽���ķ�ʽ��������
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("�������ֽ���: " + transferCount + "����ʱ: " + (System.currentTimeMillis() - startTime));
        fileChannel.close();
        socketChannel.close();
    }
}
