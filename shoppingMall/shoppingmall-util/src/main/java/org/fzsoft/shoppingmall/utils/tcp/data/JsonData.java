package org.fzsoft.shoppingmall.utils.tcp.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by yhj on 17/4/7.
 */
public class JsonData extends TcpData {
    public int code;
    public Object data;

    public JsonData(int code) {
        this.code = code;
    }

    public JsonData(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public String toDataString() {
        return JSONObject.toJSONString(this);
    }
}
