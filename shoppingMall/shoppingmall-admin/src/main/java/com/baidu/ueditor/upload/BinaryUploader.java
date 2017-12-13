package com.baidu.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.songbai.mutual.utils.prop.SpringProperties;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

public class BinaryUploader {

    private static SpringProperties properties;
    private static String bucketName;
    private static String url;
    private static String pkg;

    static {
       
        properties = SpringProperties.getBean(SpringProperties.class);

        if (properties != null) {
            bucketName = properties.getProperty("sys.admin.baiduupload.bucketName");
            url = properties.getProperty("sys.admin.baiduupload.url");
            pkg = properties.getProperty("sys.admin.baiduupload.pkg");
        }


    }


    public static final State save(HttpServletRequest request,
                                   Map<String, Object> conf) {
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        ServletFileUpload upload = new ServletFileUpload(
                new DiskFileItemFactory());

        if (isAjaxUpload) {
            upload.setHeaderEncoding("UTF-8");
        }

        try {
            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                fileStream = iterator.next();

                if (!fileStream.isFormField())
                    break;
                fileStream = null;
            }

            if (fileStream == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }
            String savePath = (String) conf.get("savePath");
            String originFileName = fileStream.getName();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());
            savePath = savePath + suffix;


            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);
            String title = savePath;

            String physicalPath = (String) conf.get("rootPath") + savePath;

            InputStream is = fileStream.openStream();
//			State storageState = StorageManager.saveFileByInputStream(is,
//					physicalPath, maxSize);
            savePath = pkg + savePath;

            /**
             * ali上传图片方法
             */
            State storageState = StorageManager.saveInputStreamToAli(bucketName, savePath, is);

            is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("url", "https://" + bucketName + url + pkg + title);
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
                storageState.putInfo("title", title);
                // size 的大小设定
//				storageState.putInfo("size", );
            }


            return storageState;
        } catch (FileUploadException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
