package com.jingyu.middle.config;

import android.content.Context;

import com.jingyu.utils.function.DirHelper;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class Config {

    enum RunEnvironment {
        DEV, // 开发
        TEST, //测试
        RELEASE // 项目上线
    }

    //TODO 项目上线前改为RELEASE
    public final static RunEnvironment CURRENT_RUN_ENVIRONMENT = RunEnvironment.DEV;

    /**
     * 调试设置
     */
    // 是否弹出调试的土司
    public final static boolean IS_SHOW_DEBUG_TOAST;
    // 是否打印日志到控制台
    public final static boolean IS_LOG_2_CONSOLE;
    // 错误信息日志信息是否写到文件里
    public final static boolean IS_ERROR_INFO_2_FILE;
    // 是否启用内存泄漏检测库
    public final static boolean IS_INIT_LEAK_CANARY;
    // 是否初始化crashHandler
    public final static boolean IS_INIT_CRASH_HANDLER;
    // 是否打印异常的日志到屏幕（只有在IS_INIT_CRASH_HANDLER为true时，该设置才有效）
    public final static boolean IS_SHOW_EXCEPTION_ACTIVITY;

    /**
     * 域名设置
     */
    // 通用
    public final static String HOST;
    // 通知
    public final static String PUSH;
    // html
    public final static String HTML;

    static {
        if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.DEV) {
            IS_SHOW_DEBUG_TOAST = true;
            IS_LOG_2_CONSOLE = true;
            IS_ERROR_INFO_2_FILE = true;
            IS_INIT_LEAK_CANARY = true;
            IS_INIT_CRASH_HANDLER = true;
            IS_SHOW_EXCEPTION_ACTIVITY = true;
            HOST = "http://host/dev";
            PUSH = "http://push/dev";
            HTML = "http://html/dev";
        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.TEST) {
            IS_SHOW_DEBUG_TOAST = false;
            IS_LOG_2_CONSOLE = true;
            IS_ERROR_INFO_2_FILE = true;
            IS_INIT_LEAK_CANARY = true;
            IS_INIT_CRASH_HANDLER = true;
            IS_SHOW_EXCEPTION_ACTIVITY = true;
            HOST = "http://host/test";
            PUSH = "http://push/test";
            HTML = "http://html/test";
        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.RELEASE) {
            IS_SHOW_DEBUG_TOAST = false;
            IS_LOG_2_CONSOLE = false;
            IS_ERROR_INFO_2_FILE = true;
            IS_INIT_LEAK_CANARY = false;
            IS_INIT_CRASH_HANDLER = false;
            IS_SHOW_EXCEPTION_ACTIVITY = false;
            HOST = "http://host/release";
            PUSH = "http://push/release";
            HTML = "http://html/release";
        } else {
            throw new RuntimeException("Config-->RunEnvironment的值有误");
        }
    }

    // 通用接口
    public static String getHostUrl(String key) {
        return HOST + key;
    }

    // 通知接口
    public static String getPushUrl(String key) {
        return PUSH + key;
    }

    // html接口
    public static String getHtmlUrl(String key) {
        return HTML + key;
    }


    /**
     * 目录名设置
     */
    //TODO 修改app的名称
    private final static String APP_DIR_NAME = "app_sample_02_25";
    // 图片加载库的缓存目录
    private final static String IMAGE_LOADER_CACHE_DIR_NAME = APP_DIR_NAME + File.separator + "imageLoaderCache";
    // log目录
    private final static String LOG_DIR_NAME = APP_DIR_NAME + File.separator + "log";
    // crash目录
    private final static String CRASH_LOG_DIR_NAME = APP_DIR_NAME + File.separator + "crash";

    public static File getAppDir(Context context) {
        return DirHelper.getAndroidDir(context, APP_DIR_NAME);
    }

    public static File getImageLoaderCacheDir(Context context) {
        return DirHelper.getAndroidDir(context, IMAGE_LOADER_CACHE_DIR_NAME);
    }

    public static File getLogDir(Context context) {
        return DirHelper.getAndroidDir(context, LOG_DIR_NAME);
    }

    public static File getCrashDir(Context context) {
        return DirHelper.getAndroidDir(context, CRASH_LOG_DIR_NAME);
    }
}