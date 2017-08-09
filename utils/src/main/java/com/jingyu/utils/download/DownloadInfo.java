package com.jingyu.utils.download;

import com.jingyu.utils.application.PlusBean;

import java.io.File;

/**
 * Created by jingyu on 2017/3/5.
 */

public class DownloadInfo extends PlusBean {
    // 下载保存到文件
    private File file;
    // 下载地址
    private String url = "";

    private String title = "";
    private String content = "";

    // 是否强制升级
    private boolean isForceUpgrade;
    // 是否需要断点下载
    private boolean isRange;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean range) {
        isRange = range;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isForceUpgrade() {
        return isForceUpgrade;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        isForceUpgrade = forceUpgrade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
