package com.jingyu.android.banner;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        BannerActivity.actionStart(getActivity());
    }
}
