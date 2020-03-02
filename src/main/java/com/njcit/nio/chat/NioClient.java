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
                    //�Ƿ��������¼�
                    if(selectionKey.isConnectable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        //�Ƿ��ڵȴ�����
                        if(client.isConnectionPending()){
                            //������������
                            client.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + ",���ӳɹ�").getBytes());
                            writeBuffer.flip();
                            //��buffer�е�����д�뵽ͨ����
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while(true){
                                    try{
                                        writeBuffer.clear();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                                        //�ӿ���̨��ȡ�û���������
                                        String sendMessage = bufferedReader.readLine();
                                        writeBuffer.put(sendMessage.getBytes());
                                        writeBuffer.flip();
                                        //��buffer�е�����д�뵽ͨ����
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
                    //��ȡ�������˷��͹�������Ϣ����
                    else if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel)selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        //��ͨ���е����ݶ�ȡ��buffer��
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
