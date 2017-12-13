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

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.fzsoft.shoppingmall.utils.tcp.NettyHeartHandler;
import org.fzsoft.shoppingmall.utils.tcp.Server;
import org.fzsoft.shoppingmall.utils.tcp.data.TcpData;

import java.util.concurrent.TimeUnit;

/**
 * Created by 12 on 2017/3/15
 * <p>
 * websocket 支持列表
 * <li>Safari 5+ (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 6-13 (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 14+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Chrome 16+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * <li>Firefox 7+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Firefox 11+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * </ul>
 */
public class WebSocketServer extends Server {

    private String path = "/ws.do";
    private int idleTime = 5 ;

    @Override
    protected void addChannelHandler(ChannelPipeline pipeline) {
        WebSocketFrameHandler serverHandler = new WebSocketFrameHandler();
        serverHandler.setMessageHandler(messageHandler);

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(path, null, true));
        pipeline.addLast(new IdleStateHandler(idleTime, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new NettyHeartHandler(new NettyHeartHandler.HeartMsg() {

            @Override
            public Object msg() {
                return new TextWebSocketFrame(JSON.toJSONString(TcpData.createJsonData(TcpData.REQ_HEART_PING)));
            }
        }));
        pipeline.addLast(serverHandler);
        setServerHandler(serverHandler);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }
}
