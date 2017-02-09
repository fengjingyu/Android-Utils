package com.example.middle;

import android.app.Application;
import android.content.Context;

import com.example.middle.config.ConfigFile;
import com.example.middle.config.ConfigImages;
import com.example.middle.config.ConfigLog;
import com.example.middle.config.ConfigUrl;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionBean;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.exception.IException2Server;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.helper.SPHelper;
import com.jingyu.utils.http.asynchttp.AsyncClient;
import com.jingyu.utils.imageloader.AsynLoader;
import com.jingyu.utils.util.UtilIoAndr;
import com.jingyu.utils.util.UtilScreen;
import com.jingyu.utils.util.UtilSystem;

/**
 * @email fengjingyu@foxmail.com
 * @description 初始化的顺序不要去改动
 */
public class App extends Application {

    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        createDir();

        initLog();

        initSp();

        initImageLoader();

        initHttp();

        initCrash();

        simpleDeviceInfo();

    }

    public static Application getApplication() {
        return instance;
    }

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
    public void simpleDeviceInfo() {
        Logger.i("域名环境--" + ConfigUrl.CURRENT_RUN_ENVIRONMENT.toString());
        Logger.i("日志环境--" + ConfigLog.DEBUG_CONTROL.toString());

        // Logger.i("cpuinfo--" + UtilSystem.getCPUInfos());
        Logger.i("deviceId--" + UtilSystem.getDeviceId(getApplicationContext()));
        Logger.i("model--" + UtilSystem.getModel());
        Logger.i("operatorName--" + UtilSystem.getOperatorName(getApplicationContext()));
        Logger.i("osversion--" + UtilSystem.getOSVersion());
        Logger.i("osversionsdkint--" + UtilSystem.getOSVersionSDKINT());
        Logger.i("phonebrand--" + UtilSystem.getPhoneBrand());
        Logger.i("simNum--" + UtilSystem.getSimSerialNum(getApplicationContext()));
        Logger.i("language--" + UtilSystem.getSysLanguage());
        Logger.i("versionCode--" + UtilSystem.getVersionCode(getApplicationContext()));
        Logger.i("versionName--" + UtilSystem.getVersionName(getApplicationContext()));

        Logger.i("screenHeightPx--" + UtilScreen.getScreenHeightPx(getApplicationContext()));
        Logger.i("screenWidthPx--" + UtilScreen.getScreenWidthPx(getApplicationContext()));
        Logger.i("getDensity--" + UtilScreen.getDensity(getApplicationContext()));
        Logger.i("screenHeightDP--" + UtilScreen.getScreenHeightDP(getApplicationContext()));
        Logger.i("screenWidthDP--" + UtilScreen.getScreenWidthDP(getApplicationContext()));
        Logger.i("statusBarHeightPx--" + UtilScreen.getStatusBarHeight(getApplicationContext()));
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
}
