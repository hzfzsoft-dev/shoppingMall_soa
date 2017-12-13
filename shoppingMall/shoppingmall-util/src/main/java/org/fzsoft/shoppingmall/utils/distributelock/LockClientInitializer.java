package org.fzsoft.shoppingmall.utils.distributelock;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.fzsoft.shoppingmall.utils.distributelock.codec.SimpleMessageCodec;

public class LockClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new SimpleMessageCodec());
		pipeline.addLast(new LockClientHandler());
	}

}
