package com.jingyu.android.test;

import com.jingyu.android.init.LaunchActivity;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilSystem;

/**
 * 动态权限的问题:初始化从application放到了LaunchActiity
 */
public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        AppMainActivity.actionStart(getActivity());
        Logger.i("channel--" + UtilSystem.getAppMetaData(getApplicationContext(), "CHANNEL"));
    }

}
