package com.jingyu.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.middle.config.ConfigFile;
import com.jingyu.test.R;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.utils.util.UtilIoAndr;

import java.io.File;
import java.util.ArrayList;

public class LogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        testLog();
    }

    private void testLog() {
        Logger.i(1);
        Logger.i(null);
        Logger.i(true);
        Logger.i(false);
        Logger.i("demo");
        Logger.i(new Object());
        Object obj = null;
        Logger.i(obj);

        //Logger.shortToast(1);
        Logger.shortToast(null);
        //Logger.shortToast(new Object());
        //Logger.shortToast("demo");
        //Logger.shortToast(false);

        //Logger.dLongToast(1);
        //Logger.dLongToast(null);
        //Logger.dLongToast(new Object());
        //Logger.dLongToast("demo");
        Logger.dLongToast(false);

        try {
            Logger.e("123");
            Logger.e("345");
            Logger.e("678");
            int i = 1 / 0;
        } catch (Exception e) {
            Logger.e(this, "--oncreate()--", e);
        }
        // Logger.clearLog();
        Logger.e(this, "1234567890");
        Logger.tempPrint("android--" + System.currentTimeMillis());

        Logger.i(UtilIo.getAllFilesByDirQueue(UtilIoAndr.createDirInSDCard(ConfigFile.APP_ROOT), new ArrayList<File>()));

        UtilIo.toFileByBytes(UtilIoAndr.createFileInAndroid(this, ConfigFile.APP_ROOT, "lalala.txt"), "写入的内容--1234567890987654321abc".getBytes(), true);
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, LogActivity.class));
    }

}
