package com.njcit.grpc;

import com.njcit.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.Random;
import java.util.UUID;

/**
 * @Author LiJun
 * @Date 2020/2/10 18:21
 */

public class StudentServerImpl extends StudentServiceGrpc.StudentServiceImplBase {
    /**
     * 客户端向服务器发出一个请求，服务器返回一个响应
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("getRealNameByUsername invoked");
        System.out.println("from client: " + request.getUsername());
        //服务端返回结果给客户端
        responseObserver.onNext(MyResponse.newBuilder().setRealname("from server: lijun").build());
        responseObserver.onCompleted();//标识该方法已经结束
    }

    /**
     * 客户端向服务器发出一个请求，服务器返回一个响应流
     * @param request
     * @param responseObserver
     */
    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("----------------------------------");
        System.out.println("接到到客户端信息: " + request.getAge());
        //向服务端发出响应
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("吴梦瑶").
                setAge(22).
                setCity("南京汉庭").
                build());
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("高康").
                setAge(24).
                setCity("上海").
                build());
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("辛维国").
                setAge(26).
                setCity("连云港").
                build());
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("周润林").
                setAge(30).
                setCity("南京站").
                build());
        //标识服务端的处理完毕了
        responseObserver.onCompleted();
    }

    /**
     * 客户端向服务器发出一个请求流，服务器返回一个响应
     * @param responseObserver
     */
    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        System.out.println("----------------------------------");
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("from client: " + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse response1 = StudentResponse
                        .newBuilder()
                        .setName("java")
                        .setAge(25)
                        .setCity("USA")
                        .build();
                StudentResponse response2 = StudentResponse
                        .newBuilder()
                        .setName("linux")
                        .setAge(30)
                        .setCity("UK")
                        .build();
                //将两个响应添加到自定义的响应集合中
                StudentResponseList studentResponseList = StudentResponseList.
                        newBuilder()
                        .addStudentResponse(response1)
                        .addStudentResponse(response2)
                        .build();
                //向客户端返回响应
                responseObserver.onNext(studentResponseList);
                //标识服务端方法结束
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 客户端向服务器发出一个请求流，服务器返回一个响应流
     *
     * @param responseObserver
     */
    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {

        return new StreamObserver<StreamRequest>(){

            @Override
            public void onNext(StreamRequest value) {
                System.out.println("from client: " + value.getRequestInfo());
                //服务器一接收到客户端数据就立马向客户端返回一个UUID作为响应
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                //服务端流关闭
                responseObserver.onCompleted();
            }
        };
    }
}
