package com.jingyu.android.glide;

import com.jingyu.android.init.*;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        MainActivity.actionStart(getActivity());
    }
}
