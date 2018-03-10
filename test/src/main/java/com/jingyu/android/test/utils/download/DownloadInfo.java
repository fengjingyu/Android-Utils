package com.jingyu.android.test.utils.download;

import com.jingyu.java.mytool.basic.bean.CloneBean;

import java.io.File;

/**
 * Created by jingyu on 2017/3/5.
 */

public class DownloadInfo extends CloneBean {
    // 下载保存到文件
    private File file;
    // 下载地址
    private String url = "";

    private String title = "";
    private String content = "";

    // 是否强制升级
    private Upgrade upgrade;
    // 是否需要断点下载
    private boolean isRange;

    public enum Upgrade {
        FORCE, CHOICE, NONE
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public boolean isForceUpgrade() {
        return upgrade == Upgrade.FORCE;
    }

    public boolean isChoiceUpgrade() {
        return upgrade == Upgrade.CHOICE;
    }

    public boolean isNoneUpgrade() {
        return upgrade == Upgrade.NONE;
    }

}
