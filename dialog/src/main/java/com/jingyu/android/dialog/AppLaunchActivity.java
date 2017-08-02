package com.jingyu.android.dialog;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        DialogActivity.actionStart(getActivity());
    }
}
