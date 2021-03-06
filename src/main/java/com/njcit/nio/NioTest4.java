package com.njcit.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author LiJun
 * @Date 2020/2/20 9:33
 * 通过NIO将文件内容读取到另一个文件当中
 */
public class NioTest4 {
    public static void main(String[] args) {

        try(FileInputStream fileInputStream = new FileInputStream("input.txt");
            FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel()
            )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocate(5);
            while(true){
                byteBuffer.clear();
                //从读通道中获取数据读取到buffer中
                int read = inputChannel.read(byteBuffer);
                System.out.println("read: " + read);
                if(-1 == read){
                    break;
                }
                byteBuffer.flip();
                //将buffer中的数据写入到写通道中
                outputChannel.write(byteBuffer);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
