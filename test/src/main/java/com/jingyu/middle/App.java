package com.jingyu.middle;

import android.app.Application;

import com.jingyu.middle.config.Config;
import com.jingyu.test.service.MyService;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.http.asynchttp.AsyncClient;
import com.jingyu.utils.util.UtilScreen;
import com.jingyu.utils.util.UtilSystem;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author fengjingyu@foxmail.com
 */
public class App extends Application {

    static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (UtilSystem.isMainProcess(getApplicationContext())) {
            instance = this;

            initLeakCanary();

            initLog();

            initSp();

            initImageLoader();

            initHttp();

            initCrashHandler();

            simpleDeviceInfo();

            initService();
        }
    }

    public static Application getApplication() {
        return instance;
    }

    private void initLog() {
        Logger.Options options = new Logger.Options();
        options.isLog2Console = Config.IS_LOG_2_CONSOLE;
        options.isLog2File = Config.IS_LOG_2_FILE;
        options.isShowDebugToast = Config.IS_SHOW_DEBUG_TOAST;
        options.logDirName = Config.LOG_DIR_NAME;
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

    private void initCrashHandler() {
        if (Config.IS_INIT_CRASH_HANDLER) {
            CrashHandler.getInstance().init(getApplication(), Config.IS_SHOW_EXCEPTION_ACTIVITY, Config.CRASH_LOG_DIR_NAME);
        }
    }

    private void initLeakCanary() {
        if (Config.IS_INIT_LEAK_CANARY) {
            LeakCanary.install(this);
        }
    }

    /**
     * 设备启动时，输出设备与app的基本信息
     */
    public void simpleDeviceInfo() {
        Logger.i("域名环境--" + Config.CURRENT_RUN_ENVIRONMENT);

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

    // 测试用
    private void initService() {
        MyService.actionStart(getApplicationContext());
    }

}
