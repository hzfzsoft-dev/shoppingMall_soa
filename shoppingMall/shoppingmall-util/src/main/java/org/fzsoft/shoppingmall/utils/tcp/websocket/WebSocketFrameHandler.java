/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.fzsoft.shoppingmall.utils.tcp.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fzsoft.shoppingmall.utils.tcp.ConnectionTerminator;
import org.fzsoft.shoppingmall.utils.tcp.ServerHandler;
import org.fzsoft.shoppingmall.utils.tcp.data.TcpData;

import java.util.concurrent.TimeUnit;

/**
 * Created by 12 on 2017/3/15.
 */
public class WebSocketFrameHandler extends ServerHandler<WebSocketFrame> {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    public WebSocketFrameHandler() {
        setLog(LOG);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.executor().schedule(new ConnectionTerminator(ctx), 10, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String msg = ((TextWebSocketFrame) frame).text();
            paraseData(ctx, msg);
        }
    }

    protected void write(Channel ch, TcpData data) {
        ch.writeAndFlush(new TextWebSocketFrame(data.toDataString()));
    }
}
