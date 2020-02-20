package com.njcit.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author LiJun
 * @Date 2020/2/19 10:01
 */

public class NioTest3 {
    public static void main(String[] args) throws Exception{
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] bytes = "hello world java".getBytes();

        for (int i = 0; i < bytes.length; i++) {
            byteBuffer.put(bytes[i]);
        }
        byteBuffer.flip();
        //将缓冲区的数据写入通道中
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
