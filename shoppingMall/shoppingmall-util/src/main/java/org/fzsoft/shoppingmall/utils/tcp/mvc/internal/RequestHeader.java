package org.fzsoft.shoppingmall.utils.tcp.mvc.internal;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * Created by yhj on 17/4/8.
 */
public class RequestHeader {
    private String encoding = "utf-8";
    private String contentType = "application/json";
    private String protocol = "http";

    private String cmd;
    private String host;
    private String token1;
    private String token2;
    private String address;
    private String scheme;
    private String method;

    public static RequestHeader parse(JSONObject jsonObject) {

        return TypeUtils.castToJavaBean(jsonObject, RequestHeader.class);
    }

    public static RequestHeader parse(String json) {

        return parse((JSONObject) JSONObject.parse(json));
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return scheme;
    }

    public String toRequestUri() {
        return scheme + "://" + host + "/" + cmd;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
