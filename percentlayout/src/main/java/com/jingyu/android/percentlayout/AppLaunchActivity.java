package com.jingyu.android.percentlayout;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {
    @Override
    protected void action() {
        PercentLayoutActivity.actionStart(getActivity());
    }
}
