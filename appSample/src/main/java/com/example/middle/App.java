package com.example.middle;

import android.app.Application;
import android.content.Context;

import com.example.middle.config.ConfigFile;
import com.example.middle.config.ConfigImages;
import com.example.middle.config.ConfigLog;
import com.example.middle.config.ConfigUrl;
import com.xiaocoder.utils.exception.CrashHandler;
import com.xiaocoder.utils.exception.ExceptionBean;
import com.xiaocoder.utils.exception.ExceptionDb;
import com.xiaocoder.utils.exception.IException2Server;
import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.function.helper.LogHelper;
import com.xiaocoder.utils.function.helper.SPHelper;
import com.xiaocoder.utils.http.asynchttp.AsyncClient;
import com.xiaocoder.utils.imageloader.AsynLoader;
import com.xiaocoder.utils.util.UtilIoAndr;
import com.xiaocoder.utils.util.UtilScreen;
import com.xiaocoder.utils.util.UtilSystem;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class App extends Application {

    private static Application instance;

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        appContext = this;

        createDir();

        initLog();

        initSp();

        initImageLoader();

        initHttp();

        initCrash();

        printEnvironment();

        simpleDeviceInfo();

    }

    public static Context getAppContext() {
        return appContext;
    }

    public static Application getApplication() {
        return instance;
    }

    private void printEnvironment() {
        LogHelper.i(Constants.TAG_SYSTEM_OUT, ConfigUrl.CURRENT_RUN_ENVIRONMENT.toString() + "-----域名环境");

        LogHelper.i(Constants.TAG_SYSTEM_OUT, ConfigLog.DEBUG_CONTROL.toString() + "-----日志环境");
    }

    /**
     * sp保存文件名 与 模式
     */
    private void initSp() {
        SPHelper.initSP(getApplicationContext(), ConfigFile.SP_FILE, Context.MODE_APPEND);// Context.MODE_MULTI_PROCESS
    }

    private void initLog() {

        LogHelper.initLog(getApplicationContext(),
                ConfigLog.IS_DTOAST, ConfigLog.IS_OUTPUT, ConfigLog.IS_PRINTLOG,
                ConfigFile.APP_ROOT, ConfigFile.LOG_FILE, Constants.ENCODING_UTF8);
    }

    private void createDir() {
        // 应用存储日志 缓存等信息的顶层文件夹
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.APP_ROOT);
        // 图片视频等缓存的文件夹
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.MOIVE_DIR);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.VIDEO_DIR);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.PHOTO_DIR);
        // crash文件夹
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.CRASH_DIR);
        // cache文件夹
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.CACHE_DIR);
    }

    private void initHttp() {
        Http.initHttp(new AsyncClient());
    }

    private void initImageLoader() {

        Image.initImager(new AsynLoader(ConfigImages.getImageloader(getApplicationContext()), ConfigImages.default_image_options));
    }

    private void initCrash() {

        CrashHandler.getInstance().init(ConfigLog.IS_INIT_CRASH_HANDLER, getApplicationContext(), ConfigFile.CRASH_DIR, ConfigLog.IS_SHOW_EXCEPTION_ACTIVITY);

        CrashHandler.getInstance().setUploadServer(new IException2Server() {
            @Override
            public void uploadException2Server(String info, Throwable ex, Thread thread,
                                               ExceptionBean model, ExceptionDb db) {
                // 如果IS_INIT_CRASH_HANDLER（枚举值中可设置）为false，则dao为空
                if (db != null) {
                    model.setUserId(Sp.getUserId());
                    db.updateByUniqueId(model, model.getUniqueId());
                }
            }
        });
    }

    /**
     * 设备启动时，输出设备与app的基本信息
     */
    public String simpleDeviceInfo() {
        return (UtilSystem.getDeviceId(getAppContext()) + "--deviceId , "
                + UtilSystem.getVersionCode(getAppContext()) + "--versionCode , "
                + UtilSystem.getVersionName(getAppContext()) + "--versionName , "
                + UtilScreen.getScreenHeightPx(getAppContext()) + "--screenHeightPx , "
                + UtilScreen.getScreenWidthPx(getAppContext()) + "--screenWidthPx , "
                + UtilScreen.getDensity(getAppContext()) + "--density , "
                + UtilScreen.getScreenHeightDP(getAppContext()) + "--screenHeightDP , "
                + UtilScreen.getScreenWidthPx(getAppContext()) + "--screenWidthDP),"
                + UtilScreen.getStatusBarHeight(getAppContext()) + "--statusBarHeightPx");
    }
}
