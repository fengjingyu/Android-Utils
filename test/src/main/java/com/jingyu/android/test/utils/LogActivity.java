package com.jingyu.android.test.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingyu.android.middle.AppFile;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
import com.jingyu.utils.util.UtilIo;
import com.jingyu.utils.function.Logger;

import java.io.File;
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
                Logger.d(1);
                Logger.d(null);
                Logger.d(true);
                Logger.d(false);
                Logger.d("demo");
                Logger.d(new Object());
                Object obj = null;
                Logger.d(obj);

                Logger.d(UtilIo.getAllFilesByDirQueue(AppFile.getAppDir(getApplicationContext())));
                Logger.d(UtilIo.getFilterFiles(AppFile.getAppDir(getApplicationContext()), new LinkedList<File>(), null));
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
                    e.printStackTrace();
                    Logger.e(getActivity() + "--oncreate()--", e);
                }
                Logger.e("1234567890");
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
