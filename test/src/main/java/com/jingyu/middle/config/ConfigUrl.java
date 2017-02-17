package com.jingyu.middle.config;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class ConfigUrl {

    /**
     * 当前的运行环境，即域名的控制, 上线前，改为ONLINE
     */
    private static RunEnvironment RUN_ENVIRONMENT = RunEnvironment.ONLINE;

    public enum RunEnvironment {
        DEV, TEST, ONLINE
    }

    /**
     * 通用
     */
    private static String HOST = "";
    /**
     * IM
     */
    private static String CHAT = "";
    /**
     * 通知
     */
    private static String PUSH = "";
    /**
     * html
     */
    private static String HTML = "";

    static {
        initConfig();
    }

    /**
     * 初始化环境
     */
    public synchronized static void initConfig() {
        if (RUN_ENVIRONMENT == RunEnvironment.ONLINE) {
            HOST = "http://host";
            CHAT = "http://chat";
            PUSH = "http://push";
            HTML = "http://html";
        } else if (RUN_ENVIRONMENT == RunEnvironment.TEST) {
            HOST = "http://host";
            CHAT = "http://chat";
            PUSH = "http://push";
            HTML = "http://html";
        } else if (RUN_ENVIRONMENT == RunEnvironment.DEV) {
            HOST = "http://host";
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

    public static RunEnvironment getRunEnvironment() {
        return RUN_ENVIRONMENT;
    }


}
