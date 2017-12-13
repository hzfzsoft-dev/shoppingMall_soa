package org.fzsoft.shoppingmall.utils.upload;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * Created by yhj on 17/6/8.
 */
public class ImageHelper {

    private static final String default_suffix = ".png";

    //图片处理方法
    public static String dataPic(String pic, String prefix, String suffix) {

        if (StringUtils.isEmpty(pic)) {
            return null;
        }
        byte[] bs = Base64.getDecoder().decode(pic);
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bs);
        String portrait = null;
        String objectKey = getObjectKey(prefix, suffix);
        try {
            portrait = AliyunOssHelper.saveInputStreamToAli(objectKey, byteInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portrait;

    }


    private static String getObjectKey(String prefix, String suffix) {
        Long time = System.currentTimeMillis();
        String objectKey = prefix + time + getSuffix(suffix);
        return objectKey;
    }


    public static String getSuffix(String suffix) {

        if (StringUtils.isEmpty(suffix)) {
            return default_suffix;
        } else {

            return suffix.startsWith(".") ? suffix : "." + suffix;

        }
    }

}
