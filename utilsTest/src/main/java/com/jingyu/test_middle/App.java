package com.jingyu.test_middle;

import android.app.Application;
import android.content.Context;

import com.jingyu.test_middle.config.ConfigUrl;
import com.jingyu.utils.imageloader.AsynLoader;
import com.jingyu.utils.util.UtilIoAndr;
import com.jingyu.utils.util.UtilScreen;
import com.jingyu.utils.util.UtilSystem;
import com.jingyu.test_middle.config.ConfigFile;
import com.jingyu.test_middle.config.ConfigImages;
import com.jingyu.test_middle.config.ConfigLog;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionBean;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.exception.IException2Server;
import com.jingyu.utils.http.asynchttp.AsyncClient;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.helper.SPHelper;

/**
 * @email fengjingyu@foxmail.com
 * @description 初始化的顺序不要去改动
 */
public class App extends Application {
    private static Application instance;

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        appContext = this;

        // initLeakCanary();

        createDir();

        initLog();

        initSp();

        initImageLoader();

        initHttp();

        initCrash();

        printEnvironment();

    }

    public static Context getAppContext() {
        return appContext;
    }

    public static Application getApplication() {
        return instance;
    }

    private void printEnvironment() {
        Logger.i(Constants.TAG_SYSTEM_OUT, ConfigUrl.CURRENT_RUN_ENVIRONMENT.toString() + "-----域名环境");

        Logger.i(Constants.TAG_SYSTEM_OUT, ConfigLog.DEBUG_CONTROL.toString() + "-----日志环境");
    }

    private void initLeakCanary() {
        if (!(ConfigLog.DEBUG_CONTROL == ConfigLog.DebugControl.CLOSE)) {

//            if (SDK_INT >= GINGERBREAD) {
//                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
//                        .detectAll()
//                        .penaltyLog()
//                        .penaltyDeath()
//                        .build());
//            }

            // LeakCanary.install(this);
        }
    }

    /**
     * sp保存文件名 与 模式
     */
    private void initSp() {
        SPHelper.initSP(getApplicationContext(), ConfigFile.SP_FILE, Context.MODE_APPEND);// Context.MODE_MULTI_PROCESS
    }

    private void initLog() {

        Logger.initLog(getApplicationContext(),
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
