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
     * �ͻ��������������һ�����󣬷���������һ����Ӧ
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("getRealNameByUsername invoked");
        System.out.println("from client: " + request.getUsername());
        //����˷��ؽ�����ͻ���
        responseObserver.onNext(MyResponse.newBuilder().setRealname("from server: lijun").build());
        responseObserver.onCompleted();//��ʶ�÷����Ѿ�����
    }

    /**
     * �ͻ��������������һ�����󣬷���������һ����Ӧ��
     * @param request
     * @param responseObserver
     */
    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("----------------------------------");
        System.out.println("�ӵ����ͻ�����Ϣ: " + request.getAge());
        //�����˷�����Ӧ
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("������").
                setAge(22).
                setCity("�Ͼ���ͥ").
                build());
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("�߿�").
                setAge(24).
                setCity("�Ϻ�").
                build());
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("��ά��").
                setAge(26).
                setCity("���Ƹ�").
                build());
        responseObserver.onNext(StudentResponse.newBuilder().
                setName("������").
                setAge(30).
                setCity("�Ͼ�վ").
                build());
        //��ʶ����˵Ĵ��������
        responseObserver.onCompleted();
    }

    /**
     * �ͻ��������������һ��������������������һ����Ӧ
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
                //��������Ӧ��ӵ��Զ������Ӧ������
                StudentResponseList studentResponseList = StudentResponseList.
                        newBuilder()
                        .addStudentResponse(response1)
                        .addStudentResponse(response2)
                        .build();
                //��ͻ��˷�����Ӧ
                responseObserver.onNext(studentResponseList);
                //��ʶ����˷�������
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * �ͻ��������������һ��������������������һ����Ӧ��
     *
     * @param responseObserver
     */
    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {

        return new StreamObserver<StreamRequest>(){

            @Override
            public void onNext(StreamRequest value) {
                System.out.println("from client: " + value.getRequestInfo());
                //������һ���յ��ͻ������ݾ�������ͻ��˷���һ��UUID��Ϊ��Ӧ
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                //��������ر�
                responseObserver.onCompleted();
            }
        };
    }
}
