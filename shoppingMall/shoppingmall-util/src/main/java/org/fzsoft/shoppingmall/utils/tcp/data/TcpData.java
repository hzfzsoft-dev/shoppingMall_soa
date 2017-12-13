package org.fzsoft.shoppingmall.utils.tcp.data;

/**
 * Created by yhj on 17/4/7.
 */
public abstract class TcpData {

    public static final int REQ_HEART_PING = 101;
    public static final int REQ_HEART_PONG = 102;



    public abstract String toDataString();


    public static JsonData createJsonData(int code) {
        return createJsonData(code, null);
    }

    public static JsonData createJsonData(int code, Object data) {

        return new JsonData(code, data);
    }
}
