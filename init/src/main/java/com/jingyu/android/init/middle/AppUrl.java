package com.jingyu.android.init.middle;


import com.jingyu.android.init.middle.config.MyEnv;

/**
 * @author fengjingyu@foxmail.com
 */
public class AppUrl {

    // 通用接口
    public static String getHostUrl(String key) {
        return MyEnv.getHOST() + key;
    }

    // 通知接口
    public static String getPushUrl(String key) {
        return MyEnv.getPUSH() + key;
    }

    // html接口
    public static String getHtmlUrl(String key) {
        return MyEnv.getHTML() + key;
    }

    //TODO Url
    public static final String LOGIN = "/app/auth/login/";
}
