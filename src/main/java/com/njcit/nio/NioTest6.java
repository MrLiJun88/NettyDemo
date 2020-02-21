package com.njcit.nio;

import java.nio.ByteBuffer;

/**
 * @Author LiJun
 * @Date 2020/2/20 10:31
 * Slice Buffer ��ԭ�е�Buffer����ײ�����
 */

public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        buffer.position(2);
        buffer.limit(6);
        //sliceBuffer��buffer��һ�����գ�����һ������
        ByteBuffer sliceBuffer = buffer.slice();
        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());
        while(buffer.hasRemaining()){
            System.out.print(buffer.get() + " ");
        }
    }
}
