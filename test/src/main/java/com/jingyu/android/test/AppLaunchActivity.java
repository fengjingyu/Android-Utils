package com.jingyu.android.test;

import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.SystemUtil;
import com.jingyu.android.init.LaunchActivity;

/**
 * 动态权限的问题:初始化从application放到了LaunchActiity
 */
public class AppLaunchActivity extends LaunchActivity {

    @Override
    protected void action() {
        AppMainActivity.actionStart(getActivity());
        Logger.i("channel--" + SystemUtil.getAppMetaData(getApplicationContext(), "CHANNEL"));
    }

}
