package com.njcit.nio;

import java.nio.ByteBuffer;

/**
 * @Author LiJun
 * @Date 2020/2/20 10:48
 * ֻ�� Buffer,���ǿ�����ʱ��һ����ͨbuffer����asReadOnlyBuffer()��������һ��ֻ��buffer
 * �����ܽ�һ��ֻ��bufferת��Ϊ��дbuffer
 */

public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        //����ͨbufferת��ֻ��buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        readOnlyBuffer.position(0);
//        readOnlyBuffer.put((byte)2);

    }
}
