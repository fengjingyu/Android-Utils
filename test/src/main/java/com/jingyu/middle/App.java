package com.jingyu.middle;

import android.app.Application;

import com.jingyu.middle.config.ConfigDir;
import com.jingyu.middle.config.ConfigLog;
import com.jingyu.middle.config.ConfigUrl;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionBean;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.exception.IException2Server;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.http.asynchttp.AsyncClient;
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

    private void initLog() {
        Logger.Options options = new Logger.Options();
        options.isLog2Console = ConfigLog.isLog2Console();
        options.isLog2File = ConfigLog.isLog2File();
        options.isShowDebugToast = ConfigLog.isShowDebugToast();
        options.logDir = ConfigDir.getLogDirName();
        Logger.initLog(getApplication(), options);
    }

    private void initSp() {
        Sp.initSP(getApplication());
    }

    private void initHttp() {
        Http.initHttp(new AsyncClient());
    }

    private void initImageLoader() {
        Image.initImage(getApplication());
    }

    private void initCrash() {
        CrashHandler.getInstance().init(ConfigLog.isInitCrashHandler(), getApplication(), ConfigDir.getCrashLogDirName(), ConfigLog.isShowExceptionActivity());
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
        Logger.i("域名环境--" + ConfigUrl.getRunEnvironment().toString());
        Logger.i("日志环境--" + ConfigLog.getDebugControl().toString());

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
        if (!(ConfigLog.getDebugControl() == ConfigLog.DebugControl.CLOSE)) {

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
