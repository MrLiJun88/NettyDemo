package com.njcit.nio.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author LiJun
 * @Date 2020/2/23 11:42
 */

public class NioClient {
    public static void main(String[] args) throws IOException {
        try{
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));

            while(true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {
                    //是否是连接事件
                    if(selectionKey.isConnectable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        //是否在等待连接
                        if(client.isConnectionPending()){
                            //真正建立连接
                            client.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + ",链接成功").getBytes());
                            writeBuffer.flip();
                            //将buffer中的数据写入到通道中
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while(true){
                                    try{
                                        writeBuffer.clear();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                                        //从控制台获取用户输入内容
                                        String sendMessage = bufferedReader.readLine();
                                        writeBuffer.put(sendMessage.getBytes());
                                        writeBuffer.flip();
                                        //将buffer中的内容写入到通道中
                                        client.write(writeBuffer);
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        client.register(selector,SelectionKey.OP_READ);
                    }
                    //获取服务器端发送过来的消息数据
                    else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel)selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        //将通道中的数据读取到buffer中
                        int count = client.read(readBuffer);
                        if(count > 0){
                            String receiveMessage = new String(readBuffer.array(),0,count);
                            System.out.println("from server : " + receiveMessage);
                        }
                    }
                }
                selectionKeys.clear();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
