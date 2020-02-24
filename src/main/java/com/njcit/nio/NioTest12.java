package com.njcit.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author LiJun
 * @Date 2020/2/21 15:14
 * 使用一个线程来接收五个端口号的连接与请求
 */

public class NioTest12 {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            //配置不阻塞模式
            socketChannel.configureBlocking(false);
            //获取到与这个通道相关联的ServerSocket对象
            ServerSocket serverSocket = socketChannel.socket();
            InetSocketAddress socketAddress = new InetSocketAddress(ports[i]);
            //绑定端口号
            serverSocket.bind(socketAddress);
            //将通道注册到给定的selector上，并注册感兴趣的事件
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口: " + ports[i]);
        }

        while(true){
            int numbers = selector.select();
            System.out.println("numbers: " + numbers);
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            System.out.println("selectionKeySet: " + selectionKeySet);
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector,SelectionKey.OP_READ);

                    iterator.remove();
                    System.out.println("获得客户端连接: " + socketChannel);
                }
                else if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int byteRead = 0;
                    while(true){
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if(read <= 0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteRead += read;
                    }
                    System.out.println("读取到的字节数: " + byteRead + " ,来自于 " + socketChannel);
                    iterator.remove();
                }
            }
        }
    }
}
