package com.njcit.protobuf;

/**
 * @Author LiJun
 * @Date 2020/2/6 16:01
 */

public class ProtoBufTest {
    public static void main(String[] args) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("zhangsan").
                setAge(25).
                setAddress("nanjing").
                build();

        byte[] student2ByteArray = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2);
    }
}
