package com.njcit.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author LiJun
 * @Date 2020/2/20 19:07
 * ʹ��ֱ�ӻ���(�����ڴ�)���ļ����ж�ȡ
 */

public class NioTest8 {
    public static void main(String[] args) {
        try(FileInputStream fileInputStream = new FileInputStream("input.txt");
            FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel()
        )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);

            while(true){
                byteBuffer.clear();
                //�Ӷ�ͨ���л�ȡ���ݶ�ȡ��buffer��
                int read = inputChannel.read(byteBuffer);
                System.out.println("read: " + read);
                if(-1 == read){
                    break;
                }
                byteBuffer.flip();
                //��buffer�е�����д�뵽дͨ����
                outputChannel.write(byteBuffer);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
