package com.baidu.ueditor.upload;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import org.songbai.mutual.utils.prop.SpringProperties;

import java.io.*;

public class StorageManager {
    public static int BUFFER_SIZE;

    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static SpringProperties properties;


   static  {


        System.err.println("开始进行转初始化....");

        properties = SpringProperties.getBean(SpringProperties.class);

        if (properties != null) {
            BUFFER_SIZE = Integer.valueOf(properties.getProperty("sys.admin.baiduupload.BUFFER_SIZE"));
            endpoint = properties.getProperty("sys.admin.baiduupload.endpoint");
            accessKeyId = properties.getProperty("sys.admin.baiduupload.accessKeyId");
            accessKeySecret = properties.getProperty("sys.admin.baiduupload.accessKeySecret");
        }


        System.err.println("开始进行转初始化...结束." + properties + "...");
    }


    public StorageManager() {
    }

    /**
     * 添加阿里云上传图片方法
     *
     * @return
     */
    public static State saveInputStreamToAli(String bucketName, String savePath, InputStream is) {

        State state = null;
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, savePath, is);

            state = new BaseState(true);
            return state;
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    public static State saveBinaryFile(byte[] data, String path) {
        File file = new File(path);

        State state = valid(file);

        if (!state.isSuccess()) {
            return state;
        }

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bos.write(data);
            bos.flush();
            bos.close();
        } catch (IOException ioe) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true, file.getAbsolutePath());
        state.putInfo("size", data.length);
        state.putInfo("title", file.getName());
        return state;
    }

    public static State saveFileByInputStream(InputStream is, String path,
                                              long maxSize) {
        State state = null;

        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, com.baidu.ueditor.upload.StorageManager.BUFFER_SIZE);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tmpFile), com.baidu.ueditor.upload.StorageManager.BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, AppInfo.MAX_SIZE);
            }

            state = saveTmpFile(tmpFile, path);

            if (!state.isSuccess()) {
                tmpFile.delete();
            }

            return state;

        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    public static State saveFileByInputStream(InputStream is, String path) {
        State state = null;

        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, com.baidu.ueditor.upload.StorageManager.BUFFER_SIZE);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(tmpFile), com.baidu.ueditor.upload.StorageManager.BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            state = saveTmpFile(tmpFile, path);

            if (!state.isSuccess()) {
                tmpFile.delete();
            }

            return state;
        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static File getTmpFile() {
        File tmpDir = FileUtils.getTempDirectory();
        String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    private static State saveTmpFile(File tmpFile, String path) {
        State state = null;
        File targetFile = new File(path);

        if (targetFile.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }
        try {
            FileUtils.moveFile(tmpFile, targetFile);
        } catch (IOException e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true);
        state.putInfo("size", targetFile.length());
        state.putInfo("title", targetFile.getName());

        return state;
    }

    private static State valid(File file) {
        File parentPath = file.getParentFile();

        if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
            return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
        }

        if (!parentPath.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }

        return new BaseState(true);
    }
}
