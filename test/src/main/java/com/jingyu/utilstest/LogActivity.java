package com.jingyu.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.middle.config.Config;
import com.jingyu.test.R;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.function.Storager;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.utils.util.UtilIoAndr;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class LogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        getViewById(R.id.deleteLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.deleteLogFile();
            }
        });

        getViewById(R.id.loggeri).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i(1);
                Logger.i(null);
                Logger.i(true);
                Logger.i(false);
                Logger.i("demo");
                Logger.i(new Object());
                Object obj = null;
                Logger.i(obj);

                Logger.i(UtilIo.getAllFilesByDirQueue(Storager.ExternalPublic.getDir(Config.APP_NAME)));
                Logger.i(UtilIo.getFilterFiles(Storager.ExternalPublic.getDir(Config.APP_NAME), new LinkedList<File>(), null));
            }
        });

        getViewById(R.id.loggere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Logger.e("123");
                    Logger.e("345");
                    Logger.e("678");
                    int i = 1 / 0;
                } catch (Exception e) {
                    Logger.e(getActivity(), "--oncreate()--", e);
                }
                Logger.e("1234567890");
            }
        });

        getViewById(R.id.loggertemp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.write2TempFile("android--" + System.currentTimeMillis());
            }
        });

        getViewById(R.id.loggertoast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logger.shortToast(1);
                //Logger.shortToast(null);
                //Logger.shortToast(new Object());
                //Logger.shortToast("demo");
                //Logger.shortToast(false);

                //Logger.dLongToast(1);
                //Logger.dLongToast(null);
                //Logger.dLongToast(new Object());
                //Logger.dLongToast("demo");
                Logger.dLongToast(false);
                Logger.longToast(true);
            }
        });

    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, LogActivity.class));
    }

}
