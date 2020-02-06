package com.njcit.netty4protobuf.server;

import com.njcit.netty4protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author LiJun
 * @Date 2020/2/6 16:45
 */

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();

        if(dataType == MyDataInfo.MyMessage.DataType.PersonType){
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName() + " " + person.getAddress() + " " + person.getAge());
        }
        else if(dataType == MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getDogName() + " " + dog.getDogAge());
        }
        else {
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getCatName() + " " + cat.getCatCity());
        }
    }
}
