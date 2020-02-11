package com.njcit.grpc;

import com.njcit.proto.MyRequest;
import com.njcit.proto.MyResponse;
import com.njcit.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @Author LiJun
 * @Date 2020/2/10 18:40
 */

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.
                forAddress("localhost",8899).
                usePlaintext().
                build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        System.out.println(myResponse.getRealname());
    }
}
