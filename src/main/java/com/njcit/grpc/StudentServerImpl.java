package com.njcit.grpc;

import com.njcit.proto.MyRequest;
import com.njcit.proto.MyResponse;
import com.njcit.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @Author LiJun
 * @Date 2020/2/10 18:21
 */

public class StudentServerImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("getRealNameByUsername invoked");
        System.out.println("from client: " + request.getUsername());
        //服务端返回结果给客户端
        responseObserver.onNext(MyResponse.newBuilder().setRealname("from server: lijun").build());
        responseObserver.onCompleted();//标识该方法已经结束
    }
}
