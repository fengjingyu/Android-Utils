package com.jingyu.android.anim;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        AppMainActivity.actionStart(getActivity());
    }
}
