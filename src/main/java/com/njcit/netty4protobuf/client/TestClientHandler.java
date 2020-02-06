package com.njcit.netty4protobuf.client;

import com.njcit.netty4protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @Author LiJun
 * @Date 2020/2/6 16:56
 */

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    /**
     * 当通道建立好时向服务器发送消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       int randomInt = new Random().nextInt(3);
       MyDataInfo.MyMessage myMessage = null;

       if(0 == randomInt){
           myMessage = MyDataInfo.MyMessage.newBuilder().
                   setDataType(MyDataInfo.MyMessage.DataType.PersonType).
                   setPerson(MyDataInfo.Person.newBuilder().setName("lijun").setAge(25).setAddress("镇江").build()).
                   build();

       }
       else if(1 == randomInt){
           myMessage = MyDataInfo.MyMessage.newBuilder().
                   setDataType(MyDataInfo.MyMessage.DataType.DogType).
                   setDog(MyDataInfo.Dog.newBuilder().setDogName("旺财").setDogAge(4).build()).
                   build();
       }
       else {
           myMessage = MyDataInfo.MyMessage.newBuilder().
                   setDataType(MyDataInfo.MyMessage.DataType.CatType).
                   setCat(MyDataInfo.Cat.newBuilder().setCatName("tomcat").setCatCity("上海").build()).
                   build();
       }
       ctx.channel().writeAndFlush(myMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }
}
