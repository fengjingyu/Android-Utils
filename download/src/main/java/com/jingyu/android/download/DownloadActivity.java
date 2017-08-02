package com.jingyu.android.download;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.function.download.DownloadOptions;

public class DownloadActivity extends BaseActivity implements View.OnClickListener {

    Button startDownload;
    Button pauseDownload;
    Button cancelDownload;
    Button startRangeDownload;

    DownloadService.DownloadBinder downloadBinder;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        //启动服务
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        // 绑定服务
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        //TODO 权限请求

        startDownload = getViewById(R.id.startDownload);
        pauseDownload = getViewById(R.id.pauseDownload);
        cancelDownload = getViewById(R.id.cancelDownload);
        startRangeDownload = getViewById(R.id.startRangeDownload);


        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        startRangeDownload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (downloadBinder != null) {
            switch (v.getId()) {
                case R.id.startDownload:
                    downloadBinder.startDownload(new DownloadOptions("http://192.168.1.101/android/test.apk",
                            DirHelper.ExternalAndroid.getFile(getApplicationContext(), "download", "test.apk")));
                    break;
                case R.id.pauseDownload:
                    downloadBinder.pauseDownload();
                    break;
                case R.id.cancelDownload:
                    downloadBinder.cancleDownload();
                    break;
                case R.id.startRangeDownload:
                    downloadBinder.startDownload(new DownloadOptions("http://192.168.1.101/android/test_range.apk",
                            DirHelper.ExternalAndroid.getFile(getApplicationContext(), "download", "test_range.apk"), true));
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, DownloadActivity.class));
    }
}
