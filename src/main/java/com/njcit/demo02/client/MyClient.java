package com.njcit.demo02.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).
                    channel(NioSocketChannel.class).
                    handler(new MyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().close().sync();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
