package org.fzsoft.shoppingmall.utils.tcp.socket;

import io.netty.channel.ChannelPipeline;
import org.fzsoft.shoppingmall.utils.netty.DreamJSONDecoder;
import org.fzsoft.shoppingmall.utils.netty.DreamJSONEncoder;
import org.fzsoft.shoppingmall.utils.tcp.Server;

/**
 * Created by 12 on 2017/3/14.
 */
public class SocketServer extends Server {


    protected void addChannelHandler(ChannelPipeline pipeline) {
        SocketServerHandler serverHandler = new SocketServerHandler();
        serverHandler.setMessageHandler(messageHandler);

        pipeline.addLast(new DreamJSONDecoder());
        pipeline.addLast(new DreamJSONEncoder());
        pipeline.addLast(serverHandler);

//        pipeline.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
//        pipeline.addLast(new NettyHeartHandler(JSON.toJSONString(Rst.create(ReqCode.REQ_HEART_PING))));
        setServerHandler(serverHandler);

    }


}
