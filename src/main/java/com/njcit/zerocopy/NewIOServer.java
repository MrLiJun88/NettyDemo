package com.njcit.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author LiJun
 * @Date 2020/3/1 20:05
 */

public class NewIOServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while(true){

            SocketChannel socketChannel = serverSocketChannel.accept();
            //可以忽略下面设置，因为accept()方法返回的socketChannel对象一定是阻塞的，不管serverSocketChannel是不是阻塞的
            socketChannel.configureBlocking(true);
            int readCount = 0;
            while(-1 != readCount){
                try{
                    readCount = socketChannel.read(byteBuffer);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                byteBuffer.rewind();
            }
        }
    }
}
