package com.jingyu.android.pullrefresh;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        ListActivity.actionStart(getActivity());
    }
}
