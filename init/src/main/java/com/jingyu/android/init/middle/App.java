package com.jingyu.android.init.middle;

import android.support.multidex.MultiDexApplication;

/**
 * @author fengjingyu@foxmail.com
 * @description 动态权限的问题:app中的初始化转移到LaunchActivity中
 */
public class App extends MultiDexApplication {

    private static App instance;

    public static App getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
