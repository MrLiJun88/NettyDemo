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
        /* The port on which the server should run */
        int port = 8899;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServerImpl())
                .build()
                .start();
    }

    private void stop() {
        if(null != server){
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if(null != server){
            this.server.awaitTermination();
        }
    }
}
