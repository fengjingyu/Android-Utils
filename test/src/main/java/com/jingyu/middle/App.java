package com.jingyu.middle;

import android.app.Application;

import com.jingyu.middle.config.ConfigFile;
import com.jingyu.middle.config.ConfigImages;
import com.jingyu.middle.config.ConfigLog;
import com.jingyu.middle.config.ConfigUrl;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionBean;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.exception.IException2Server;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.helper.SPHelper;
import com.jingyu.utils.http.asynchttp.AsyncClient;
import com.jingyu.utils.imageloader.UniversalImageLoader;
import com.jingyu.utils.util.UtilIoAndr;
import com.jingyu.utils.util.UtilScreen;
import com.jingyu.utils.util.UtilSystem;

/**
 * @author fengjingyu@foxmail.com
 */
public class App extends Application {

    static Application instance;

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

    private void createDir() {
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.APP_ROOT);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.MOIVE_DIR);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.VIDEO_DIR);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.PHOTO_DIR);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.CRASH_DIR);
        UtilIoAndr.createDirInAndroid(getApplicationContext(), ConfigFile.CACHE_DIR);
    }

    private void initLog() {
        Logger.initLog(getApplication(), ConfigLog.IS_DTOAST, ConfigLog.IS_OUTPUT, ConfigLog.IS_PRINTLOG, ConfigFile.APP_ROOT, ConfigFile.LOG_FILE);
    }

    private void initSp() {
        Sp.initSP(new SPHelper(getApplicationContext(), ConfigFile.SP_FILE));
    }

    private void initHttp() {
        Http.initHttp(new AsyncClient());
    }

    private void initImageLoader() {
        Image.initImager(new UniversalImageLoader(ConfigImages.getImageloader(getApplicationContext()), ConfigImages.displayImageOptions));
    }

    private void initCrash() {
        CrashHandler.getInstance().init(ConfigLog.IS_INIT_CRASH_HANDLER, getApplication(), ConfigFile.CRASH_DIR, ConfigLog.IS_SHOW_EXCEPTION_ACTIVITY);
        CrashHandler.getInstance().setUploadServer(new IException2Server() {
            @Override
            public void uploadException2Server(String info, Throwable ex, Thread thread, ExceptionBean model, ExceptionDb db) {
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

        //Logger.i("cpuinfo--" + UtilSystem.getCPUInfos());
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

          /*  if (SDK_INT >= GINGERBREAD) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
            }
            LeakCanary.install(this);*/
        }
    }
}
