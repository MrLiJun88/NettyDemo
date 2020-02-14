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
        //blockingStub������ͬ���ķ�ʽ
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.
                newBlockingStub(managedChannel);
        //���첽�ķ�ʽ�����ͻ�������������͵���������������ʽʱ��ֻ��֧�����첽����ʽ����
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

        //�ͻ��������������һ�����󣬷���������һ����Ӧ
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().
                setUsername("zhangsan").
                build());
        System.out.println(myResponse.getRealname());

        //�ͻ��������������һ�����󣬷���������һ����Ӧ��
        System.out.println("-------------------------------------");
        Iterator<StudentResponse> responseIterator = blockingStub.getStudentsByAge(StudentRequest.
                newBuilder().
                setAge(22).
                build());
        //ͨ����������ӡ����Ӧ������Ϣ
        responseIterator.forEachRemaining(e -> System.out.println(e.getName()));

        System.out.println("-------------------------------------");
        //�ͻ��������������һ��������������������һ����Ӧ
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
                System.out.println("�������˷������ݽ���");
            }
        };
        //����ͻ����������Ҫ���͵�����
        StreamObserver<StudentRequest> requestStreamObserver = stub.getStudentsWrapperByAges(streamObserver);
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(1).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(2).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(3).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(4).build());
        requestStreamObserver.onCompleted();
//        Thread.sleep(50000);

        System.out.println("-------------------------------------");
        //�ͻ��������������һ��������������������һ����Ӧ��
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
                System.out.println("�����������ݷ��ؽ���");
            }
        });
        //���������˷��͵�����
        for (int i = 0; i < 10; i++) {
            observer.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            Thread.sleep(1000);
        }

        Thread.sleep(50000);
    }
}
