package org.fzsoft.shoppingmall.utils.upload;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.fzsoft.shoppingmall.utils.prop.SpringProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yhj on 17/6/8.
 */
public class AliyunOssHelper {

    private static SpringProperties properties;
    private static String bucketName;
    private static String url;
    private static String pkg;
    public static int BUFFER_SIZE;
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;

    static {

        System.err.println("开始进行转初始化....");
        properties = SpringProperties.getBean(SpringProperties.class);

        if (properties != null) {
            bucketName = properties.getProperty("sys.admin.baiduupload.bucketName");
            url = properties.getProperty("sys.admin.baiduupload.url");
            pkg = properties.getProperty("sys.admin.baiduupload.pkg");
            BUFFER_SIZE = Integer.valueOf(properties.getProperty("sys.admin.baiduupload.BUFFER_SIZE"));
            endpoint = properties.getProperty("sys.admin.baiduupload.endpoint");
            accessKeyId = properties.getProperty("sys.admin.baiduupload.accessKeyId");
            accessKeySecret = properties.getProperty("sys.admin.baiduupload.accessKeySecret");
        }
        System.err.println("开始进行转初始化...结束." + properties + "...");
    }

    public static String saveInputStreamToAli(String objectkey, InputStream is) {

        return saveInputStreamToAli(is,getContextType(objectkey),null,getStoreKey(objectkey));
    }

    public static String saveInputStreamToAli(String objectkey, String context) {

        ByteArrayInputStream is = new ByteArrayInputStream(context.getBytes());

        return saveInputStreamToAli(is,getContextType(objectkey),"utf-8",getStoreKey(objectkey));
    }


    public static String saveInputStreamToAli(String objectkey, MultipartFile multipartFile) {

        try {
            return saveInputStreamToAli(multipartFile.getInputStream(),multipartFile.getContentType(),null,getStoreKey(objectkey));
        } catch (IOException e) {
            throw new RuntimeException("上传OSS异常",e);
        }
    }


    /**
     *
     * @param objectkey
     * @param file
     * @return
     */
    public static String saveInputStreamToAli(String objectkey, File file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);


        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType(getContextType(objectkey));
        String storeKey = getStoreKey(objectkey);
        try {
            ossClient.putObject(bucketName, storeKey, file, objectMeta);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return "https://" + bucketName + url + storeKey;
    }


    /**
     *
     * @param input
     * @param contentType
     * @param storeKey
     * @return
     */
    public static String saveInputStreamToAli(InputStream input,String contentType,String contentEncoding,String storeKey) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata objectMeta = new ObjectMetadata();
        // 判断上传类型，多的可根据自己需求来判定
        objectMeta.setContentType(contentType);

        if(contentEncoding != null){
            objectMeta.setContentEncoding(contentEncoding);
        }

        try {
            ossClient.putObject(bucketName, storeKey, input, objectMeta);
        } finally {
            ossClient.shutdown();
        }
        return "https://" + bucketName + url + storeKey;
    }





    private static String getStoreKey(String objectkey) {

        String pkg =   properties.getProperty("sys.admin.upload.pkg","upload/");

        return pkg.endsWith("/") ? pkg + objectkey : pkg + "/" + objectkey;
    }


    private static String getContextType(String objectkey) {

        return OssContextType.getContextType(objectkey);
    }

}
