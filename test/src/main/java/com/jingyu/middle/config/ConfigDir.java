package com.jingyu.middle.config;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class ConfigDir {

    //TODO 修改app的名称
    private static String APP_NAME = "app_" + "test123";
    /**
     * 图片加载库的缓存目录
     */
    private static String IMAGE_LOADER_CACHE_DIR_NAME = APP_NAME + "/imageLoaderCacheDir";
    /**
     * log目录
     */
    private static String LOG_DIR_NAME = APP_NAME + "/log";
    /**
     * crash目录
     */
    private static String CRASH_LOG_DIR_NAME = LOG_DIR_NAME + "/crash";

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getImageLoaderCacheDirName() {
        return IMAGE_LOADER_CACHE_DIR_NAME;
    }

    public static String getLogDirName() {
        return LOG_DIR_NAME;
    }

    public static String getCrashLogDirName() {
        return CRASH_LOG_DIR_NAME;
    }

}