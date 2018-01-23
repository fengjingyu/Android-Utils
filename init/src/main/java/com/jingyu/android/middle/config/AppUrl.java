package com.jingyu.android.middle.config;

/**
 * Created by jingyu on 2018/1/23.
 */
public class AppUrl {

    // 通用接口
    public static String getHostUrl(String key) {
        return Config.getHOST() + key;
    }

    // 通知接口
    public static String getPushUrl(String key) {
        return Config.getPUSH() + key;
    }

    // html接口
    public static String getHtmlUrl(String key) {
        return Config.getHTML() + key;
    }

    //TODO Url
    public static final String LOGIN = "/app/auth/login/";
}
