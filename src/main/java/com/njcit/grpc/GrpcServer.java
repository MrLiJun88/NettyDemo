package com.njcit.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @Author LiJun
 * @Date 2020/2/10 18:29
 */

public class GrpcServer {

    private Server server;

    public static void main(String[] args) throws Exception{
        final GrpcServer server = new GrpcServer();
        server.start();
        //�ȴ��رգ����������һ�������Զ��ر�
        server.awaitTermination();
    }

    private void start() throws IOException {
        int port = 8899;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServerImpl())
                .build()
                .start();
        System.out.println("server started");
        //�ص�����
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("JVM�˳�");
            GrpcServer.this.stop();
        }));
//        System.out.println("ִ�е�����");
    }

    private void stop() {
        System.out.println("����ر�");
        if(null != server){
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if(null != server){
            //����3���ӣ���������Զ��˳�
//            this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);
            this.server.awaitTermination();
        }
    }
}
