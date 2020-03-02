package com.njcit.nio;

import java.nio.ByteBuffer;

/**
 * @Author LiJun
 * @Date 2020/2/20 10:23
 * ByteBuffer类型化的put与get方法
 */

public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(15);
        byteBuffer.putLong(50000000L);
        byteBuffer.putDouble(14.1548);
        byteBuffer.putChar('L');
        byteBuffer.putShort((short)2);
        byteBuffer.putChar('J');

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());
    }
}
