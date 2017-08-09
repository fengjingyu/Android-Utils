package com.jingyu.android.leakcanary;

import com.jingyu.android.init.LaunchActivity;
import com.jingyu.android.middle.config.Config;
import com.squareup.leakcanary.LeakCanary;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        initLeakCanary();
        LeakCanaryActivity.actionStart(getActivity());
    }

    private void initLeakCanary() {
        if (Config.isInitLeakCanary()) {
            LeakCanary.install(getApplication());
        }
    }
}
