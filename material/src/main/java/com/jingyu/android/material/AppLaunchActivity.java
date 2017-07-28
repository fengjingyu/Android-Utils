package com.jingyu.android.material;


import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        super.action();
        AppMainActivity.actionStart(getActivity());
    }
}
