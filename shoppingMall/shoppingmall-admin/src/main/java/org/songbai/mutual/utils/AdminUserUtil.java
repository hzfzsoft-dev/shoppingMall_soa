package org.songbai.mutual.utils;
import org.songbai.mutual.admin.model.AdminMenuResourceModel;
import org.songbai.mutual.admin.model.AdminUserModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * Created by yhj on 17/6/7.
 */
public class AdminUserUtil implements ApplicationContextAware {
    private final static String ENCRYPT_AES_KEY = "@]S8(xG5+0_S8X(s$=";


    private final static String TOKEN_SPLIT = "@";
    private final static String COOKIE_ACCESS_TOKEN_NAME = "a_u_l_t";
    private final static int USER_LOGIN_TIME_OUT = 4 * 60 * 60;


    private static final String URL_ACCESS_ALL_REDIS_KEY = "admin:access:url:all";
    private static final String URL_ACCESS_HASH_KEY = "admin:access:url";
    private static final String URL_ACCESS_MENU_HASH_KEY = "admin:access:menu";
    private static final String URL_ACCESS_USER_HASH_KEY = "admin:access:user";

    private static RedisTemplate<String, Object> redisTemplate;


    private static ThreadLocal<AdminUserModel> userModelThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<WebRequest> requestThreadLocal = new ThreadLocal<>();

    public static void recordRequest(WebRequest request) {
        requestThreadLocal.set(request);


        checkUserAndFlushCookie(request);
    }


    public static void login(AdminUserModel adminUserModel) {

        WebRequest request = requestThreadLocal.get();

        AdminUserModel userModel = (AdminUserModel) request.getSessionAttribute("user");
        List<String> allUrlAccess = (List<String>) request.getSessionAttribute("allUrlAccess");
        List<String> urlAccess = (List<String>) request.getSessionAttribute("urlAccess");
        List<AdminMenuResourceModel> adminMenu = (List<AdminMenuResourceModel>) request.getSessionAttribute("menu");


        flushUserCookie(request, adminUserModel.getId());

        redisTemplate.opsForValue().set(URL_ACCESS_ALL_REDIS_KEY, allUrlAccess);
        redisTemplate.opsForHash().put(URL_ACCESS_HASH_KEY, adminUserModel.getId(), urlAccess);
        redisTemplate.opsForHash().put(URL_ACCESS_MENU_HASH_KEY, adminUserModel.getId(), adminMenu);
        redisTemplate.opsForHash().put(URL_ACCESS_USER_HASH_KEY, adminUserModel.getId(), userModel);
    }


    public static void logout() {
        Integer userId = getUserId();

        requestThreadLocal.get().deleteCookie(COOKIE_ACCESS_TOKEN_NAME);

//        if (userId != null) {
//            redisTemplate.opsForHash().delete(URL_ACCESS_HASH_KEY, userId);
//            redisTemplate.opsForHash().delete(URL_ACCESS_USER_HASH_KEY, userId);
//            redisTemplate.opsForHash().delete(URL_ACCESS_MENU_HASH_KEY, userId);
//        }
    }


    public static Integer getUserId() {

        AdminUserModel entry = userModelThreadLocal.get();

        return entry != null ? entry.getId() : null;
    }


    public static void clear() {
        userModelThreadLocal.remove();
        requestThreadLocal.remove();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisTemplate = applicationContext.getBean(RedisTemplate.class);
    }


    private static void checkUserAndFlushCookie(WebRequest request) {
        String userToken = request.getCookieValue(COOKIE_ACCESS_TOKEN_NAME);
        if (userToken == null) {
            return;
        }
        String[] userArray = null;

        try {
            userToken = EncryptAES.decrypt(userToken, ENCRYPT_AES_KEY);
            userArray = userToken.split(TOKEN_SPLIT);

            if (userArray == null || userArray.length < 2) {
                throw new RuntimeException("签名错误,请重新登录");
            }
        } catch (Exception e) {
            throw new RuntimeException("签名错误,请重新登录");
        }

        String userIdStr = userArray[0];
        String userLoginTime = userArray[1];


        long lastLoginTime = Long.parseLong(userLoginTime);

        if ((System.currentTimeMillis() - lastLoginTime) > USER_LOGIN_TIME_OUT * 1000) {
            throw new RuntimeException("登录超时");
        }

        flushUserSession(request, Integer.parseInt(userIdStr),lastLoginTime);
    }


    private static boolean flushUserSession(WebRequest request, Integer userId,long lastFlushTime) {

        if (userId == null) {
            return true;
        }

        AdminUserModel adminUserModel = (AdminUserModel) request.getSessionAttribute("user");
        if (adminUserModel == null) {
            List<String> allUrlAccess = (List<String>) redisTemplate.opsForValue().get(URL_ACCESS_ALL_REDIS_KEY);
            List<String> urlAccess = (List<String>) redisTemplate.opsForHash().get(URL_ACCESS_HASH_KEY, userId);
            List<AdminMenuResourceModel> adminMenu = (List<AdminMenuResourceModel>) redisTemplate.opsForHash().get(URL_ACCESS_MENU_HASH_KEY, userId);
            adminUserModel = (AdminUserModel) redisTemplate.opsForHash().get(URL_ACCESS_USER_HASH_KEY, userId);

            request.getSession().setAttribute("user", adminUserModel);
            request.getSession().setAttribute("userId", adminUserModel.getId());
            request.getSession().setAttribute("allUrlAccess", allUrlAccess);
            request.getSession().setAttribute("urlAccess", urlAccess);
            request.getSession().setAttribute("menu", adminMenu);
        }

        userModelThreadLocal.set(adminUserModel);

        if((System.currentTimeMillis() - lastFlushTime) > 5 * 60 * 1000){
            return flushUserCookie(request, userId);
        }

        return true;
    }

    private static boolean flushUserCookie(WebRequest request, Integer userId) {

        StringBuffer sb = new StringBuffer();

        sb.append(userId).append(TOKEN_SPLIT).append(System.currentTimeMillis()).append(TOKEN_SPLIT).append(new Random().nextInt(1000));

        String userToken = null;

        try {
            userToken = EncryptAES.encrypt(sb.toString(), ENCRYPT_AES_KEY);
        } catch (Exception e) {
            return false;
        }

        request.setCookie(COOKIE_ACCESS_TOKEN_NAME, userToken, USER_LOGIN_TIME_OUT);

        return true;
    }
}

