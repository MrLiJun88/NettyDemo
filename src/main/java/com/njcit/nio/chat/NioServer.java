package com.njcit.nio.chat;

import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author LiJun
 * @Date 2020/2/23 9:35
 */

public class NioServer {

    /**存放所有与客户端连接的信息*/
    private static Map<String,SocketChannel> clientMap = new HashMap<>(16);

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        //将serverSocketChannel注册到selector上,并关注连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //进行事件处理
        while(true){
            try{
                //返回一个整形，它所关注的事件数量
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel clientChannel;
                    try{
                        //客户端连接事件
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
                            clientChannel = serverChannel.accept();
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector,SelectionKey.OP_READ);
                            //将已经与服务器端建立好连接的客户端信息，放入map中保存
                            String key = "【" + UUID.randomUUID() + "】";
                            clientMap.put(key,clientChannel);
                            //客户端发送数据事件
                        } else if (selectionKey.isReadable()) {
                            clientChannel = (SocketChannel)selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            //将通道中的数据读取到buffer中
                            int count = clientChannel.read(readBuffer);
                            if(count > 0){
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println("client: " + clientChannel + ",from client: " + receiveMessage);

                                //通过value获得对应的key值
                                String sendKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    //获取与当前客户端channel对应的key值
                                    if(clientChannel == entry.getValue()){
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }
                                //向每个已经注册到selector上客户端返回数据，实现消息的群发
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    SocketChannel socketChannel = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((sendKey + " : " + receiveMessage).getBytes());
                                    writeBuffer.flip();
                                    //将buffer中的数据写入通道
                                    socketChannel.write(writeBuffer);
                                }
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
