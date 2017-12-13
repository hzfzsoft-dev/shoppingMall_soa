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
package org.fzsoft.shoppingmall.utils.tcp.mvc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.fzsoft.shoppingmall.utils.tcp.ServerHandler;
import org.fzsoft.shoppingmall.utils.tcp.mvc.internal.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by 12 on 2017/3/15.
 */
public class MvcWebSocketHandler extends ServerHandler<WebSocketFrame> {
    private static final Logger LOG = LoggerFactory.getLogger(MvcWebSocketHandler.class);

    private static final String REQUEST_CONTENT = "content";
    private static final String REQUEST_HEADER = "header";

    public MvcWebSocketHandler() {
        setLog(LOG);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String msg = ((TextWebSocketFrame) frame).text();

            JSONObject requestJson = null;
            try {
                requestJson = (JSONObject) JSONObject.parse(msg);
            } catch (Exception e) {
//                write(ctx.channel(), new WebResponse(WebResponse.CMD_SYSTEM_JSON, "json解析异常"));
                return;
            }


            RequestHeader header = RequestHeader.parse(requestJson.getJSONObject(REQUEST_HEADER));


            MockHttpServletRequestBuilder builder = null;
            switch (StringUtils.trimToEmpty(header.getMethod()).toLowerCase()) {
                case "get":
                    builder = MockMvcRequestBuilders.get("");
                    break;
                case "post":
                    break;
            }

            JSONObject content = requestJson.getJSONObject(REQUEST_CONTENT);

            for (String s : content.keySet()) {

                Object obj = requestJson.get(s);

                if (obj instanceof JSONObject) {

                } else if (obj instanceof JSONArray) {

                } else {

                }

            }


        }
    }


}
