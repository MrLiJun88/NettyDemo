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
                    eventType = "������";
                    break;
                }
                case WRITER_IDLE: {
                    eventType = "д����";
                    break;
                }
                case ALL_IDLE: {
                    eventType = "��д�ռ�";
                    break;
                }
            }
            System.out.println(ctx.channel().remoteAddress() + " ��ʱ�¼�: " + eventType);
            ctx.channel().close();
        }
    }
}
