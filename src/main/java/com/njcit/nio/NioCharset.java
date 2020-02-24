package com.njcit.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @Author LiJun
 * @Date 2020/2/23 12:12
 * 关于java的编解码
 */

public class NioCharset {
    public static void main(String[] args) throws Exception{
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NIoTest13_Out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        long inputLength = new File(inputFile).length();
        FileChannel inputChannel = inputRandomAccessFile.getChannel();
        FileChannel outputChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY,0,inputLength);

        System.out.println("=============================");

        Charset.availableCharsets().forEach((k,v) ->
                System.out.println(k + "," + v));

        System.out.println("=============================");

//        Charset charset = Charset.forName("utf-8");
        Charset charset = Charset.forName("iso-8859-1");
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharsetEncoder charsetEncode = charset.newEncoder();

        CharBuffer charBuffer = charsetDecoder.decode(inputData);
        ByteBuffer outputData = charsetEncode.encode(charBuffer);

        outputChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();
    }
}
