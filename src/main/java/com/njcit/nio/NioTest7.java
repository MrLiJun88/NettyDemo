package com.njcit.nio;

import java.nio.ByteBuffer;

/**
 * @Author LiJun
 * @Date 2020/2/20 10:48
 * 只读 Buffer,我们可以随时将一个普通buffer调用asReadOnlyBuffer()方法返回一个只读buffer
 * 但不能将一个只读buffer转换为读写buffer
 */

public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        //将普通buffer转成只读buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        readOnlyBuffer.position(0);
//        readOnlyBuffer.put((byte)2);

    }
}
