package org.fzsoft.shoppingmall.utils.mvc;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.fzsoft.shoppingmall.utils.prop.SpringProperties;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author Boyce 下午4:00:21
 * 本来考虑是否为UserUtil实现存放用户attr的功能，后来考虑分布式情况下，很容易userId和userAttr的失效时间不一致。
 * 且在分布式情况下，很少需要session来工作，故而放弃
 */
public class UserUtil implements ApplicationContextAware {
    public static final String SESSION_USER_ID_PREFIX = "_session_user:";
    static ThreadLocal<Integer> userIds = new ThreadLocal<>();
    static ValueOperations<String, Integer> _userIds;
    public static int expiredSeconds = 7 * 24 * 3600;

    static ApplicationContext ctx;
    static SpringProperties prop = null;


    public void setCache(RedisTemplate redisTemplate) {
        _userIds = redisTemplate.opsForValue();
    }


    public static Integer getUserId() {
        return userIds.get();
    }

    /**
     * 清理登录信息。
     * @param request
     * @param response
     */
    public static void clearUserInfo(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String token1 = null;
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(TokenUtil.TOKEN1, cookie.getName())) {
                token1 = cookie.getValue();
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            if (StringUtils.equals(TokenUtil.TOKEN2, cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        token1 = new String(Base64.decodeBase64(token1.getBytes()));
        _userIds.getOperations().delete(SESSION_USER_ID_PREFIX + token1);
        System.out.println("删除记录成功");
    }


    static void recordUser(int userId, String token) {
        userIds.set(userId);
        _userIds.set(SESSION_USER_ID_PREFIX + token, userId, expiredSeconds, TimeUnit.SECONDS);
    }

    public static void injectToken(String token) {
        Integer userId = _userIds.get(SESSION_USER_ID_PREFIX + token);
        if (userId != null) {
            userIds.set(userId);
        }
    }

    public static void flush() {
        userIds.remove();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
        prop = ctx.getBean(SpringProperties.class);
    }
}
