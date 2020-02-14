package com.njcit.grpc;

import com.njcit.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @Author LiJun
 * @Date 2020/2/10 18:40
 */

public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.
                forAddress("localhost",8899).
                usePlaintext().
                build();
        //blockingStub以阻塞同步的方式
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);
        //以异步的方式，当客户端向服务器发送的数据是以流的形式时，只能支持以异步的形式发送
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

        //客户端向服务器发出一个请求，服务器返回一个响应
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().
                setUsername("zhangsan").
                build());
        System.out.println(myResponse.getRealname());

        //客户端向服务器发出一个请求，服务器返回一个响应流
        System.out.println("-------------------------------------");
        Iterator<StudentResponse> responseIterator = blockingStub.getStudentsByAge(StudentRequest.
                newBuilder().
                setAge(22).
                build());
        //通过迭代器打印出响应流中信息
        responseIterator.forEachRemaining(e -> System.out.println(e.getName()));

        System.out.println("-------------------------------------");
        //客户端向服务器发出一个请求流，服务器返回一个响应
        StreamObserver<StudentResponseList> streamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(
                        e -> System.out.println(e.getName() + " " + e.getAge() + " " + e.getCity())
                );
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("服务器端返回数据结束");
            }
        };
        //构造客户端向服务器要发送的数据
        StreamObserver<StudentRequest> requestStreamObserver = stub.getStudentsWrapperByAges(streamObserver);
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(1).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(2).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(3).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(4).build());
        requestStreamObserver.onCompleted();
//        Thread.sleep(50000);

        System.out.println("-------------------------------------");
        //客户端向服务器发出一个请求流，服务器返回一个响应流
        StreamObserver<StreamRequest> observer = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println("from server: " + value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("服务器端数据返回结束");
            }
        });
        //构造向服务端发送的数据
        for (int i = 0; i < 10; i++) {
            observer.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            Thread.sleep(1000);
        }

        Thread.sleep(50000);
    }
}
