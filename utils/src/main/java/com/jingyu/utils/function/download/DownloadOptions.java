package com.jingyu.utils.function.download;

import java.io.File;

/**
 * Created by jingyu on 2017/3/5.
 */

public class DownloadOptions {
    // 下载地址
    private String url = "";
    // 下载保存到文件
    private File saveFile;
    // 是否需要断点下载
    private boolean isRangeDownload = false;

    public DownloadOptions(String url, File saveFile, boolean isRangeDownload) {
        this.url = url;
        this.saveFile = saveFile;
        this.isRangeDownload = isRangeDownload;
    }

    public String getUrl() {
        return url;
    }

    public File getFile() {
        return saveFile;
    }

    public boolean isRangeDownload() {
        return isRangeDownload;
    }
}
