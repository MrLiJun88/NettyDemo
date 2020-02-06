package com.njcit.demo04.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;
            switch (event.state()){
                case READER_IDLE: {
                    eventType = "¶Á¿ÕÏÐ";
                    break;
                }
                case WRITER_IDLE: {
                    eventType = "Ð´¿ÕÏÐ";
                    break;
                }
                case ALL_IDLE: {
                    eventType = "¶ÁÐ´¿Õ¼ä";
                    break;
                }
            }
            System.out.println(ctx.channel().remoteAddress() + " ³¬Ê±ÊÂ¼þ: " + eventType);
            ctx.channel().close();
        }
    }
}
