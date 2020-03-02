package com.njcit.demo;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author LiJun
 * @Date 2020/3/2 19:12
 */

public class Test {
    public static void main(String[] args) {

        System.out.println(SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    }
}
