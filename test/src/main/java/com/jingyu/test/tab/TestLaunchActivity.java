package com.jingyu.test.tab;

import com.demo.init.LaunchActivity;
import com.demo.middle.config.Config;
import com.squareup.leakcanary.LeakCanary;

/**
 * 动态权限的问题:初始化从application放到了LaunchActiity
 */
public class TestLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        initLeakCanary();

        initTestService();

        TestMainActivity.actionStart(getActivity());
    }

    private void initTestService() {
        //LocalService.actionStart(getApplicationContext());
        //AIDLService.actionStart(getApplicationContext());
    }

    private void initLeakCanary() {
        if (Config.isInitLeakCanary()) {
            LeakCanary.install(getApplication());
        }
    }
}
