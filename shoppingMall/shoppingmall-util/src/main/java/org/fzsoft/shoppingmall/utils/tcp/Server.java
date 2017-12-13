package org.fzsoft.shoppingmall.utils.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.fzsoft.shoppingmall.utils.tcp.data.TcpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Server {

    protected int port;
    private Logger logger = LoggerFactory.getLogger(Server.class);
    protected Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    protected ServerHandler serverHandler;

    protected MessageHandler messageHandler;


    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    //收到订阅某合约行情的请求
    public void sentData(TcpData data) {

        serverHandler.write(channel, data);
    }


    /**
     *
     */
    public void startServer() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    addChannelHandler(pipeline);
                }

            });
            channel = b.bind(port).sync().channel();
            logger.info("行情发送服务器 初始化完成");

        } catch (InterruptedException e) {
            logger.warn("行情发送服务器 出错...", e);
        }
    }


    protected abstract void addChannelHandler(ChannelPipeline pipeline);


    public void destroy() throws Exception {
        try {
            if (channel != null && channel.isActive()) {
                channel.close().sync();
            }
            if (bossGroup != null) {
                bossGroup.shutdownGracefully().syncUninterruptibly();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully().syncUninterruptibly();
            }

        } catch (Exception e) {
            logger.warn("行情发送服务器 关闭出错...", e);
        }
    }
}
