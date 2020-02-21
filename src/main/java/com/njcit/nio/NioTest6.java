package com.njcit.nio;

import java.nio.ByteBuffer;

/**
 * @Author LiJun
 * @Date 2020/2/20 10:31
 * Slice Buffer 与原有的Buffer共享底层数组
 */

public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        buffer.position(2);
        buffer.limit(6);
        //sliceBuffer是buffer的一个快照，共享一份数据
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
