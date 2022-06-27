package com.peter.netty.rpc.client.handler;

import com.peter.netty.rpc.codec.*;
import com.peter.netty.rpc.serializer.Serializer;
import com.peter.netty.rpc.serializer.hessian.HessianSerializer;
import com.peter.netty.rpc.serializer.kryo.KryoSerializer;
import com.peter.netty.rpc.serializer.protostuff.ProtostuffSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by luxiaoxun on 2016-03-16.
 */
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        Serializer serializer = ProtostuffSerializer.class.newInstance();
//        Serializer serializer = HessianSerializer.class.newInstance();
        Serializer serializer = KryoSerializer.class.newInstance();
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new IdleStateHandler(0, 0, Beat.BEAT_INTERVAL, TimeUnit.SECONDS));
        cp.addLast(new RpcEncoder(RpcRequest.class, serializer));
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        cp.addLast(new RpcDecoder(RpcResponse.class, serializer));
        cp.addLast(new RpcClientHandler());
    }
}
