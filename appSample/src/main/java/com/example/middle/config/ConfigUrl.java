package com.example.middle.config;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ConfigUrl {

    /**
     * 当前的运行环境，即域名的控制, 上线前，改为ONLINE
     */
    public static RunEnvironment CURRENT_RUN_ENVIRONMENT = RunEnvironment.ONLINE;

    public enum RunEnvironment {
        DEV, TEST, ONLINE
    }

    /**
     * 通用域名
     */
    public static String HOST = "";
    /**
     * 聊天域名
     */
    public static String CHAT = "";
    /**
     * 通知域名
     */
    public static String PUSH = "";
    /**
     * html域名
     */
    public static String HTML = "";

    static {
        initConfig();
    }

    /**
     * 初始化环境
     */
    public synchronized static void initConfig() {
        if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.ONLINE) {
            HOST = "http://post";
            CHAT = "http://chat";
            PUSH = "http://push";
            HTML = "http://html";
        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.TEST) {
            HOST = "http://post";
            CHAT = "http://chat";
            PUSH = "http://push";
            HTML = "http://html";
        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.DEV) {
            HOST = "http://post";
            CHAT = "http://chat";
            PUSH = "http://push";
            HTML = "http://html";
        }
    }

    /**
     * 通用接口
     *
     * @param key 地址
     * @return
     */
    public static String getUrlHost(String key) {
        return HOST + key;
    }

    /**
     * 聊天接口
     *
     * @param key 地址
     * @return
     */
    public static String getUrlChat(String key) {
        return CHAT + key;
    }

    /**
     * 通知接口
     *
     * @param key 地址
     * @return
     */
    public static String getUrlPush(String key) {
        return PUSH + key;
    }

    /**
     * html接口
     *
     * @param key 地址
     * @return
     */
    public static String getUrlHtml(String key) {
        return HTML + key;
    }

}
