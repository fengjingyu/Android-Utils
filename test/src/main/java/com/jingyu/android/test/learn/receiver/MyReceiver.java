package com.jingyu.android.test.learn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.basictools.util.SystemUtil;

/**
 * 国内定制系统:仅在程序开启或在后台时可以收到静态广播
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Logger.shortToast(SystemUtil.getProcessName(context) + "---" + intent.getAction());

    }
}
