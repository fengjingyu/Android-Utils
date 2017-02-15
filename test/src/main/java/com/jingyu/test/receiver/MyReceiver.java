package com.jingyu.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilSystem;

/**
 * 国内机型定制后的:仅在程序开启或在后台时可以收到静态广播
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Logger.shortToast(UtilSystem.getProcessName(context) + "---" + intent.getAction());

    }
}
