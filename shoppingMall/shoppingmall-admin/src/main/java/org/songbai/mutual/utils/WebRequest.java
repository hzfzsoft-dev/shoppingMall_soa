package org.songbai.mutual.utils;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by yhj on 17/6/7.
 */
public class WebRequest extends ServletWebRequest {

    public static final int COOKIE_EXPIRY  = 60*15;

    public WebRequest(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }


    public String getIpAddr() throws Exception {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * session handler
     */

    public HttpSession getSession(){
        return getRequest().getSession();
    }
    public Object getSessionAttribute(String name){
        return getSession().getAttribute(name);
    }
    public void removeSessionAttribute(String name){
        getSession().removeAttribute(name);
    }
    public void setSessionAttribute(String name,Object obj){
        getSession().setAttribute(name, obj);
    }


    /**
     * cookie handler
     *
     */

    public String getCookieValue(String name){
        Cookie cookie =  getCookie(name);
        if(cookie == null){
            return null;
        }

        return cookie.getValue();
    }

    public void deleteCookie(String name ){
        setCookie(name,"",0);
    }

    public void setCookie(String name ,String value,Integer expiry){
        Cookie cookie =  new Cookie(name, value);

        if(expiry == null) {
            cookie.setMaxAge(COOKIE_EXPIRY);
        }else{
            cookie.setMaxAge(expiry);
        }


        cookie.setPath(getRequest().getServletContext().getContextPath());
//        String host = getRequest().getHeader("Host");
//        if(host != null){
//            cookie.setDomain(host);
//        }

        getResponse().addCookie(cookie);
    }

    public Cookie getCookie(String name){
        Cookie[] cookies =  getRequest().getCookies();
        if(cookies == null){
            return null;
        }
        for (Cookie cookie : getRequest().getCookies()) {
            if(cookie.getName().equals(name)){
                return cookie;
            }
        }
        return null;
    }


}



