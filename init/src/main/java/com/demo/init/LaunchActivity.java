package com.demo.init;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.demo.middle.Http;
import com.demo.middle.Image;
import com.demo.middle.Sp;
import com.demo.middle.base.BaseActivity;
import com.demo.middle.config.Config;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.okhttp.OkClient;
import com.jingyu.utils.util.UtilScreen;
import com.jingyu.utils.util.UtilSystem;

import java.util.ArrayList;

/**
 * 动态权限的问题:初始化从application放到了LaunchActivity
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    private void checkPermission() {
        boolean writePermission = isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean phoneStatePermission = isPermissionGranted(Manifest.permission.READ_PHONE_STATE);

        if (writePermission && phoneStatePermission) {
            init();
        } else {
            ArrayList<String> list = new ArrayList<String>();

            if (writePermission) {
                list.add(Manifest.permission.READ_PHONE_STATE);
            } else if (phoneStatePermission) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                list.add(Manifest.permission.READ_PHONE_STATE);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            permissionRequest(list.toArray(new String[list.size()]), REQUEST_CODE);
        }
    }

    public static final int REQUEST_CODE = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                boolean isAllSuccess = true;
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        isAllSuccess = false;
                        break;
                    }
                }
                if (isAllSuccess) {
                    init();
                } else {
                    UtilSystem.toSetting(getActivity());
                    Toast.makeText(getApplicationContext(), "权限被拒绝了", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void init() {
        initLog();

        initSp();

        initImageLoader();

        initHttp();

        initCrashHandler();

        simpleDeviceInfo();

        MainActivity.actionStart(getActivity());

        finish();
    }

    private void initLog() {
        Logger.Options options = new Logger.Options();
        options.consoleLogLevel = Config.getConsoleLogLevel();
        options.isErrorLog2File = Config.isErrorLog2File();
        options.isShowDebugToast = Config.isShowDebugToast();
        options.logDir = Config.getLogDir(getApplicationContext());
        Logger.initLog(getApplication(), options);
    }

    private void initSp() {
        Sp.initSP(getApplication());
    }

    private void initHttp() {
        Http.initHttp(new OkClient());
    }

    private void initImageLoader() {
        Image.initImage(getApplication());
    }

    private void initCrashHandler() {
        if (Config.isInitCrashHandler()) {
            CrashHandler.getInstance().init(getApplication(), Config.isShowExceptionActivity(), Config.getCrashDir(getApplicationContext()));
        }
    }

    // 设备启动时，输出设备与app的基本信息
    public void simpleDeviceInfo() {
        Logger.d("域名环境--" + Config.CURRENT_RUN_ENVIRONMENT);
        Logger.d("deviceId--" + UtilSystem.getDeviceId(getApplicationContext()));
        Logger.d("model--" + UtilSystem.getModel());
        Logger.d("operatorName--" + UtilSystem.getOperatorName(getApplicationContext()));
        Logger.d("osversion--" + UtilSystem.getOSVersion());
        Logger.d("osversionsdkint--" + UtilSystem.getOSVersionSDKINT());
        Logger.d("phonebrand--" + UtilSystem.getPhoneBrand());
        Logger.d("simNum--" + UtilSystem.getSimSerialNum(getApplicationContext()));
        Logger.d("language--" + UtilSystem.getSysLanguage());
        Logger.d("versionCode--" + UtilSystem.getVersionCode(getApplicationContext()));
        Logger.d("versionName--" + UtilSystem.getVersionName(getApplicationContext()));
        Logger.d("screenHeightPx--" + UtilScreen.getScreenHeightPx(getApplicationContext()));
        Logger.d("screenWidthPx--" + UtilScreen.getScreenWidthPx(getApplicationContext()));
        Logger.d("getDensity--" + UtilScreen.getDensity(getApplicationContext()));
        Logger.d("getDensityDpi--" + UtilScreen.getDensityDpi(getApplicationContext()));
        Logger.d("screenHeightDP--" + UtilScreen.getScreenHeightDP(getApplicationContext()));
        Logger.d("screenWidthDP--" + UtilScreen.getScreenWidthDP(getApplicationContext()));
        Logger.d("statusBarHeightPx--" + UtilScreen.getStatusBarHeight(getApplicationContext()));
    }

}
