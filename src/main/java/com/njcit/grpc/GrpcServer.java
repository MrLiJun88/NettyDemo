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
        //等待关闭，避免服务器一启动就自动关闭
        server.awaitTermination();
    }

    private void start() throws IOException {
        int port = 8899;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServerImpl())
                .build()
                .start();
        System.out.println("server started");
        //回调钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("JVM退出");
            GrpcServer.this.stop();
        }));
//        System.out.println("执行到这里");
    }

    private void stop() {
        System.out.println("服务关闭");
        if(null != server){
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if(null != server){
            //超过3秒钟，则服务器自动退出
//            this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);
            this.server.awaitTermination();
        }
    }
}
