package com.njcit.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author LiJun
 * @Date 2020/2/21 9:55
 * 内存映射文件,对于内存映射文件的修改会直接影响到磁盘上的文件
 */

public class NioTest9 {

    public static void main(String[] args) throws Exception{

        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte)'c');
        map.put(3,(byte)'b');
        randomAccessFile.close();

    }
}
