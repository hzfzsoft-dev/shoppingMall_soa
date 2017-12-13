package org.fzsoft.shoppingmall.utils.tcp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fzsoft.shoppingmall.utils.tcp.data.TcpData;

import java.io.IOException;


public abstract class ServerHandler<T> extends SimpleChannelInboundHandler<T> {

    private Logger LOG = LoggerFactory.getLogger(ServerHandler.class);

    private MessageHandler messageHandler;

    protected void setLog(Logger log) {
        LOG = log;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    protected void write(Channel ch, TcpData data) {
        ch.writeAndFlush(data.toDataString());
    }


    protected void paraseData(ChannelHandlerContext ctx, String msg) throws IOException {
        ctx.channel().attr(AttributeKey.valueOf("heart")).set(System.currentTimeMillis());
        ctx.channel().attr(AttributeKey.valueOf("stats")).set(System.currentTimeMillis());

        LOG.info("msg ={}", msg);
        JSONObject obj = JSON.parseObject(msg);
        int code = obj.getInteger("code");

        handlerMsg(code, obj.getString("data"), ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("客户端连接websocket 服务器 {},{}", ctx.channel().remoteAddress(), ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        discardChannel(ctx.channel());
        LOG.info("客户端断开websocket 服务器 {},{}", ctx.channel().remoteAddress(), ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        discardChannel(ctx.channel());

        LOG.error("异常情况 {}", cause);
    }


    private void discardChannel(Channel ch) {
        if (ch.isActive()) ch.close();
    }


    private void handlerMsg(int code, String data, Channel session) {

        switch (code) {
            case TcpData.REQ_HEART_PING:
                write(session, TcpData.createJsonData(TcpData.REQ_HEART_PONG));
                break;
            case TcpData.REQ_HEART_PONG:
                break;
            default:
                messageHandler.handler(data);

        }

    }
}
