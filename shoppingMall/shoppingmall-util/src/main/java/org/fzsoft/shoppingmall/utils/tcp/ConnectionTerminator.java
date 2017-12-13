package org.fzsoft.shoppingmall.utils.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * Created by 12 on 2017/3/16.
 */
public class ConnectionTerminator implements Runnable {

    ChannelHandlerContext ctx;

    public ConnectionTerminator(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        //如果没有这个值说明没有收到消息 说明要断开连接
        if (!ctx.channel().hasAttr(AttributeKey.valueOf("stats"))) {
            ctx.close();
        }
    }
}
