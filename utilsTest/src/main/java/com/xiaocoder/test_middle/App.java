package com.xiaocoder.test_middle;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.xiaocoder.test_middle.config.ConfigFile;
import com.xiaocoder.test_middle.config.ConfigImages;
import com.xiaocoder.test_middle.config.ConfigLog;
import com.xiaocoder.test_middle.config.ConfigUrl;
import com.xiaocoder.utils.application.XCConstant;
import com.xiaocoder.utils.exception.XCCrashHandler;
import com.xiaocoder.utils.exception.XCExceptionModel;
import com.xiaocoder.utils.exception.XCExceptionModelDb;
import com.xiaocoder.utils.exception.XCIException2Server;
import com.xiaocoder.utils.http.asynchttp.XCAsyncClient;
import com.xiaocoder.utils.imageloader.XCAsynLoader;
import com.xiaocoder.utils.io.XCIOAndroid;
import com.xiaocoder.utils.io.XCLog;
import com.xiaocoder.utils.io.XCSP;
import com.xiaocoder.utils.util.UtilScreen;
import com.xiaocoder.utils.util.UtilSystem;

/**
 * @author xiaocoder
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

        initLeakCanary();

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
        XCLog.i(XCConstant.TAG_SYSTEM_OUT, ConfigUrl.CURRENT_RUN_ENVIRONMENT.toString() + "-----域名环境");

        XCLog.i(XCConstant.TAG_SYSTEM_OUT, ConfigLog.DEBUG_CONTROL.toString() + "-----日志环境");
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

            LeakCanary.install(this);
        }
    }

    /**
     * sp保存文件名 与 模式
     */
    private void initSp() {
        XCSP.initXCSP(getApplicationContext(), ConfigFile.SP_FILE, Context.MODE_APPEND);// Context.MODE_MULTI_PROCESS
    }

    private void initLog() {

        XCLog.initXCLog(getApplicationContext(),
                ConfigLog.IS_DTOAST, ConfigLog.IS_OUTPUT, ConfigLog.IS_PRINTLOG,
                ConfigFile.APP_ROOT, ConfigFile.LOG_FILE, XCConstant.ENCODING_UTF8);
    }

    private void createDir() {
        // 应用存储日志 缓存等信息的顶层文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.APP_ROOT);
        // 图片视频等缓存的文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.MOIVE_DIR);
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.VIDEO_DIR);
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.PHOTO_DIR);
        // crash文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.CRASH_DIR);
        // cache文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.CACHE_DIR);
    }

    private void initHttp() {
        Http.initHttp(new XCAsyncClient());
    }

    private void initImageLoader() {

        Image.initImager(new XCAsynLoader(ConfigImages.getImageloader(getApplicationContext()), ConfigImages.default_image_options));
    }

    private void initCrash() {

        XCCrashHandler.getInstance().init(ConfigLog.IS_INIT_CRASH_HANDLER, getApplicationContext(), ConfigFile.CRASH_DIR, ConfigLog.IS_SHOW_EXCEPTION_ACTIVITY);

        XCCrashHandler.getInstance().setUploadServer(new XCIException2Server() {
            @Override
            public void uploadException2Server(String info, Throwable ex, Thread thread,
                                               XCExceptionModel model, XCExceptionModelDb db) {
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
