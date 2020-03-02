package com.njcit.zerocopy.old;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Author LiJun
 * @Date 2020/3/1 19:50
 */

public class OldClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",8899);

        String fileName = "G:\\Gepoint.rar";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();

        while (-1 != (readCount = inputStream.read(buffer))){
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("�������ֽ���: " + total + "����ʱ: " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        inputStream.close();
        socket.close();;
    }
}
