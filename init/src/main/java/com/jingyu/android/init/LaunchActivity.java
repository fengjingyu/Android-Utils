package com.jingyu.android.init;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.jingyu.android.basictools.exception.CrashHandler;
import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.basictools.util.ScreenUtil;
import com.jingyu.android.basictools.util.SystemUtil;
import com.jingyu.android.middle.AppFile;
import com.jingyu.android.middle.AppHttp;
import com.jingyu.android.middle.AppImg;
import com.jingyu.android.middle.AppSp;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.config.MyEnv;
import com.jingyu.android.middle.config.okhttp.MyHttpClient;
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
                    SystemUtil.toSetting(getActivity());
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

        action();

        finish();
    }

    protected void action() {
        MainActivity.actionStart(getActivity());
    }

    private void initLog() {
        Logger.Options options = new Logger.Options();
        options.consoleLogLevel = MyEnv.getConsoleLogLevel();
        options.isErrorLog2File = MyEnv.isErrorLog2File();
        options.isShowDebugToast = MyEnv.isShowDebugToast();
        options.logDir = AppFile.getLogDir(getApplicationContext());
        Logger.initLog(getApplication(), options);
    }

    private void initSp() {
        AppSp.initSP(getApplication());
    }

    private void initHttp() {
        AppHttp.initHttp(new MyHttpClient());
    }

    private void initImageLoader() {
        AppImg.initImg(getApplication());
    }

    private void initCrashHandler() {
        if (MyEnv.isInitCrashHandler()) {
            CrashHandler.getInstance().init(getApplication(), MyEnv.isShowExceptionActivity(), AppFile.getCrashDir(getApplicationContext()));
        }
    }

    // 设备启动时，输出设备与app的基本信息
    public void simpleDeviceInfo() {
        Logger.d("域名环境--" + MyEnv.CURRENT_RUN_ENVIRONMENT);
        Logger.d("deviceId--" + SystemUtil.getDeviceId(getApplicationContext()));
        Logger.d("model--" + SystemUtil.getModel());
        Logger.d("operatorName--" + SystemUtil.getOperatorName(getApplicationContext()));
        Logger.d("osversion--" + SystemUtil.getOSVersion());
        Logger.d("osversionsdkint--" + SystemUtil.getOSVersionSDKINT());
        Logger.d("phonebrand--" + SystemUtil.getPhoneBrand());
        Logger.d("simNum--" + SystemUtil.getSimSerialNum(getApplicationContext()));
        Logger.d("language--" + SystemUtil.getSysLanguage());
        Logger.d("versionCode--" + SystemUtil.getVersionCode(getApplicationContext()));
        Logger.d("versionName--" + SystemUtil.getVersionName(getApplicationContext()));
        Logger.d("screenHeightPx--" + ScreenUtil.getScreenHeightPx(getApplicationContext()));
        Logger.d("screenWidthPx--" + ScreenUtil.getScreenWidthPx(getApplicationContext()));
        Logger.d("getDensity--" + ScreenUtil.getDensity(getApplicationContext()));
        Logger.d("getDensityDpi--" + ScreenUtil.getDensityDpi(getApplicationContext()));
        Logger.d("screenHeightDP--" + ScreenUtil.getScreenHeightDP(getApplicationContext()));
        Logger.d("screenWidthDP--" + ScreenUtil.getScreenWidthDP(getApplicationContext()));
        Logger.d("statusBarHeightPx--" + ScreenUtil.getStatusBarHeight(getApplicationContext()));
    }

}
