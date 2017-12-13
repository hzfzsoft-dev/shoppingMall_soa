package org.fzsoft.shoppingmall.utils.tcp.socket;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fzsoft.shoppingmall.utils.tcp.ServerHandler;

/**
 * Created by 12 on 2017/3/14.
 */
public class SocketServerHandler extends ServerHandler<String> {
    private static final Logger LOG = LoggerFactory.getLogger(SocketServerHandler.class);

    public SocketServerHandler() {
        setLog(LOG);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        paraseData(ctx, msg);
    }


}
