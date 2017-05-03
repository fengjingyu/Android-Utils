package com.jingyu.utils.http;

import java.io.File;

import okhttp3.MediaType;

/**
 * @author fengjingyu@foxmail.com
 */
public class UploadFileWrapper {

    private File file;

    public UploadFileWrapper(File file) {
        this.file = file;
    }

    public String getName() {
        if (isExist()) {
            return file.getName();
        } else {
            return "file not exist " + System.currentTimeMillis();
        }
    }

    public File getFile() {
        return file;
    }

    public MediaType getMediaType() {
        if (isExist()) {
            return getFileContentType();
        } else {
            return null;
        }
    }

    private MediaType getFileContentType() {

//        if (file.getName().lastIndexOf("png") > 0 || file.getName().lastIndexOf("PNG") > 0) {
//            return MediaType.parse("image/png; charset=UTF-8");
//        }
//
//        if (file.getName().lastIndexOf("jpg") > 0 || file.getName().lastIndexOf("JPG") > 0
//                || file.getName().lastIndexOf("jpeg") > 0 || file.getName().lastIndexOf("JPEG") > 0) {
//            return MediaType.parse("image/jpeg; charset=UTF-8");
//        }

        return MediaType.parse("application/octet-stream");
    }

    public long length() {
        if (isExist()) {
            return file.length();
        } else {
            return 0;
        }
    }

    public boolean isExist() {
        return file != null && file.exists();
    }

}