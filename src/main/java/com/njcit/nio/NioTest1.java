package com.njcit.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @Author LiJun
 * @Date 2020/2/19 9:27
 */

public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        System.out.println("capacity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());

        System.out.println("-----------------------------");
        for (int i = 0; i < 5; i++) {
            int randomNum = new SecureRandom().nextInt(20);
            intBuffer.put(randomNum);
        }
        System.out.println("before limit: " + intBuffer.limit());
        intBuffer.flip();
        System.out.println("after limit: " + intBuffer.limit());

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
