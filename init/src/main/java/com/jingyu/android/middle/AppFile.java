package com.jingyu.android.middle;

import android.content.Context;

import com.jingyu.android.middle.config.MyEnv;
import com.jingyu.utils.function.FileHelper;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 */
public class AppFile {

    // photo目录名
    private final static String PHOTO_DIR_NAME = "photo";
    // log目录名
    private final static String LOG_DIR_NAME = "log";
    // crash目录名
    private final static String CRASH_DIR_NAME = "crash";

    /**
     * app目录
     */
    public static File getAppDir(Context context) {
        return FileHelper.getAndroidDir(context, MyEnv.getAppDirName());
    }

    /**
     * 图片目录
     */
    public static File getPhotoDir(Context context) {
        return FileHelper.getAndroidDir(context, MyEnv.getAppDirName() + File.separator + PHOTO_DIR_NAME);
    }

    /**
     * 日志目录
     */
    public static File getLogDir(Context context) {
        return FileHelper.getAndroidDir(context, MyEnv.getAppDirName() + File.separator + LOG_DIR_NAME);
    }

    /**
     * crash目录
     */
    public static File getCrashDir(Context context) {
        return FileHelper.getAndroidDir(context, MyEnv.getAppDirName() + File.separator + CRASH_DIR_NAME);
    }

}
