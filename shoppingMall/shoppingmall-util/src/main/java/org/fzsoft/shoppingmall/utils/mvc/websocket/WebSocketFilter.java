package org.fzsoft.shoppingmall.utils.mvc.websocket;

/**
 * Created by yhj on 17/4/7.
 */
public interface WebSocketFilter {

    public void doFilterInternal(WebSocketRequest request, FilterChain chain);
}
