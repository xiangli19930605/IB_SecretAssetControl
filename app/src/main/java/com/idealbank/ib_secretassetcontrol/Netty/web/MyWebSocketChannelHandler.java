package com.idealbank.ib_secretassetcontrol.Netty.web;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 初始化连接时候的各个组件
 */
public class MyWebSocketChannelHandler extends ChannelInitializer<SocketChannel> {
    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();
    @Override
    protected void initChannel(SocketChannel e) throws Exception {
        e.pipeline().addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
//        e.pipeline().addLast(idleStateTrigger);
        e.pipeline().addLast("http-codec", new HttpServerCodec());
        e.pipeline().addLast("aggregator", new HttpObjectAggregator(1024*1024*5));
        e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
//        e.pipeline().addLast(new WebSocketServerProtocolHandler("/send",null,false,1048576*2));
        e.pipeline().addLast("handler", new MyWebSocketHandler());


    }
}