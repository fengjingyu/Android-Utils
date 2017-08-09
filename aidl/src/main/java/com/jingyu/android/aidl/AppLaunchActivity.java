package com.jingyu.android.aidl;

import com.jingyu.android.init.LaunchActivity;

public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        AppMainActivity.actionStart(getActivity());
    }

    private void initTestService() {
        //LocalService.actionStart(getApplicationContext());
        //AIDLService.actionStart(getApplicationContext());
    }


}
