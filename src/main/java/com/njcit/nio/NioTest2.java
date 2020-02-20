package com.njcit.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author LiJun
 * @Date 2020/2/19 9:54
 */

public class NioTest2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //将通道中的数据读取到缓冲区
        fileChannel.read(byteBuffer);
        byteBuffer.flip();

        while (byteBuffer.remaining() > 0){
            byte b = byteBuffer.get();
            System.out.println("Character: " + (char)b);
        }
        fileChannel.close();
        fileInputStream.close();
    }
}
