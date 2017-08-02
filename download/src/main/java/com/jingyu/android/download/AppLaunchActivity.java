package com.jingyu.android.download;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        AppMainActivity.actionStart(getActivity());
    }
}
