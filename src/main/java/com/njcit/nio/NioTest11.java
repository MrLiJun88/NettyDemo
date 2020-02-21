package com.njcit.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author LiJun
 * @Date 2020/2/21 10:07
 * 关于Buffer的Scattering与Gathering
 */

public class NioTest11 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(socketAddress);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength){
                //将通道数据读取到buffer中
                long r = socketChannel.read(byteBuffers);
                bytesRead += r;
                System.out.println("bytesRead: " + bytesRead);
                Arrays.asList(byteBuffers).stream().map(buffer -> "position: " + buffer.position() + " , limit: " + buffer.limit() + " ,capacity: " + buffer.capacity()).
                        forEach(System.out::println);
            }
            System.out.println("-------------开始向客户端返回数据----------------");
            Arrays.stream(byteBuffers).forEach(buffer -> buffer.flip());
            //将buffer数据写到channel中
            long bytesWritten = 0;
            while(bytesWritten < messageLength){
                long w = socketChannel.write(byteBuffers);
                bytesWritten += w;
                Arrays.asList(byteBuffers).stream().map(buffer -> "position: " + buffer.position() + " , limit: " + buffer.limit() + " ,capacity: " + buffer.capacity()).
                        forEach(System.out::println);
            }
            Arrays.stream(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("bytesRead: " + bytesRead + " ,bytesWritten: " + bytesWritten + " ,messageLength: " + messageLength);
        }
    }
}
