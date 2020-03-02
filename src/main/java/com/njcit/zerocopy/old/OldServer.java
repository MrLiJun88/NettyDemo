package com.njcit.zerocopy.old;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author LiJun
 * @Date 2020/3/1 19:43
 */

public class OldServer {
    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(8899);

        while(true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try{
                byte[] byteArray = new byte[4096];
                while(true){
                    int readByte = dataInputStream.read(byteArray, 0, byteArray.length);
                    if(-1 == readByte){
                        break;
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}