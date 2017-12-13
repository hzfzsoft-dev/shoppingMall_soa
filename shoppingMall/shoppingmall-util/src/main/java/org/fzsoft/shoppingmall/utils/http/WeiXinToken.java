package org.fzsoft.shoppingmall.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fzsoft.shoppingmall.utils.math.Arith;
import org.fzsoft.shoppingmall.utils.prop.SpringProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class WeiXinToken {

    private Logger logger = LoggerFactory.getLogger(WeiXinToken.class);

    public static final String WX_TOKEN = "wx_token";
    public static final String WX_TICKET = "wx_ticket";
    public static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    public static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";


    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SpringProperties properties;

    public String getWinxinSign(String url, String timestamp, String nonceStr) {

        String accessToken = getAccessToken(true);

        String ticket = getTicket(accessToken);
        //第一次获取ticket失败，则重新获取token
        if ("0".equals(ticket)) {
            accessToken = getAccessToken(false);
        }
        logger.info("==获取accessToken："+accessToken);
        //再获取下ticket
        ticket = getTicket(accessToken);
        //如果还错，直接退出
        if ("0".equals(ticket)) {
            throw new RuntimeException("签名获取异常");
        }

        return getSignForUrl(url, timestamp, ticket, nonceStr);
    }
    
    private String getSignForUrl(String url, String timestamp, String tikect, String nonceStr) {
        String[] paramArr = new String[]{"jsapi_ticket=" + tikect, "timestamp=" + timestamp, "noncestr=" + nonceStr,
                "url=" + url};
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]).concat("&" + paramArr[3]);
        logger.info("sign for {}", content);
        String gensignature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行 sha1 加密
            byte[] digest = md.digest(content.getBytes());
            gensignature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }

        return gensignature.toLowerCase();
    }


    public String getAccessToken(boolean cache) {

        String appId = properties.getProperty("user.login.weixin.appid");
        String secret = properties.getProperty("user.login.weixin.secret");

        String accessToken = redisTemplate.opsForValue().get(WX_TOKEN);
        if (!cache || accessToken == null) {
            String tokenUrl = TOKEN_URL.replace("APPID", appId).replace("SECRET", secret);
            JSONObject tokenObj = JSON.parseObject(HttpTools.doGet(tokenUrl, null, null));

            logger.info("get accessToken : {} ", tokenObj);
            if (tokenObj.getInteger("errcode") != null && tokenObj.getInteger("errcode") != 0) {
                throw new RuntimeException("获取token异常");
            }

            accessToken = tokenObj.getString("access_token");
            Integer expiresIn = new BigDecimal(Arith.multiplys(0, tokenObj.getIntValue("expires_in"), 0.9)).intValue();

            redisTemplate.opsForValue().set(WX_TOKEN, accessToken, expiresIn, TimeUnit.SECONDS);
        }

        return accessToken;
    }


    public String getTicket(String accessToken) {

        String ticket = redisTemplate.opsForValue().get(WX_TICKET);

        if (ticket != null) {
            return ticket;
        }

        String ticketUrl = TICKET_URL.replace("ACCESS_TOKEN", accessToken);

        JSONObject tikectObj = JSON.parseObject(HttpTools.doGet(ticketUrl, null, null));

        logger.info("get ticket : {} ", tikectObj);
        //获取ticket失败，则return
        if (tikectObj.getInteger("errcode") != null && tikectObj.getInteger("errcode") != 0) {
            return "0";
        }

        ticket = tikectObj.getString("ticket");
        Integer expiresIn = new BigDecimal(Arith.divides(0, tikectObj.getIntValue("expires_in"), 10)).intValue();

        redisTemplate.opsForValue().set(WX_TICKET, ticket, expiresIn, TimeUnit.SECONDS);

        return ticket;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

}
