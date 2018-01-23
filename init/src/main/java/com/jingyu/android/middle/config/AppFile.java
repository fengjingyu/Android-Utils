package com.jingyu.android.middle.config;

import android.content.Context;

import com.jingyu.utils.function.FileHelper;

import java.io.File;

/**
 * Created by jingyu on 2018/1/23.
 */
public class AppFile {

    // 图片目录名
    private final static String IMAGE_DIR_NAME = "image";
    // log目录名
    private final static String LOG_DIR_NAME = "log";
    // crash目录名
    private final static String CRASH_DIR_NAME = "crash";

    /**
     * app目录
     */
    public static File getAppDir(Context context) {
        return FileHelper.getAndroidDir(context, Config.getAppDirName());
    }

    /**
     * 图片目录
     */
    public static File getImageDir(Context context) {
        return FileHelper.getAndroidDir(context, Config.getAppDirName() + File.separator + IMAGE_DIR_NAME);
    }

    /**
     * 日志目录
     */
    public static File getLogDir(Context context) {
        return FileHelper.getAndroidDir(context, Config.getAppDirName() + File.separator + LOG_DIR_NAME);
    }

    /**
     * crash目录
     */
    public static File getCrashDir(Context context) {
        return FileHelper.getAndroidDir(context, Config.getAppDirName() + File.separator + CRASH_DIR_NAME);
    }

}
