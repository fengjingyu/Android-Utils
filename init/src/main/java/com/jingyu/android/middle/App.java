package com.jingyu.android.middle;

import android.app.Application;

/**
 * @author fengjingyu@foxmail.com
 * @description 动态权限的问题:app中的初始化转移到LaunchActivity中
 */
public class App extends Application {

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
