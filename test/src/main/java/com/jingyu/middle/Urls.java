package com.jingyu.middle;

import com.jingyu.middle.config.Config;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class Urls {
    //TODO url

    // 通用接口
    public static String getHost(String key) {
        return Config.HOST + key;
    }

    // 通知接口
    public static String getPush(String key) {
        return Config.PUSH + key;
    }

    // html接口
    public static String getHtml(String key) {
        return Config.HTML + key;
    }

}
