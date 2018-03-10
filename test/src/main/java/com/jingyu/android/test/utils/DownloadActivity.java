package com.jingyu.android.test.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.basictools.util.AndroidFileUtil;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
import com.jingyu.android.test.utils.download.DownloadInfo;
import com.jingyu.android.test.utils.download.DownloadService;
import com.jingyu.java.mytool.basic.encryption.Md5;

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
            Logger.d(downloadBinder + "--downloadBinder");
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
        //绑定服务
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
                    DownloadInfo downloadInfo = new DownloadInfo();
                    downloadInfo.setUrl("http://192.168.0.102/android/appv2.apk");
                    downloadInfo.setFile(AndroidFileUtil.ExternalAndroid.getFile(getApplicationContext(), "download", Md5.encode(downloadInfo.getUrl()) + ".apk"));
                    downloadBinder.startDownload(downloadInfo);
                    break;
                case R.id.pauseDownload:
                    downloadBinder.pauseDownload();
                    break;
                case R.id.cancelDownload:
                    downloadBinder.cancleDownload();
                    break;
                case R.id.startRangeDownload:
                    DownloadInfo downloadInfo2 = new DownloadInfo();
                    downloadInfo2.setUrl("http://192.168.0.102/android/appv2.apk");
                    downloadInfo2.setFile(AndroidFileUtil.ExternalAndroid.getFile(getApplicationContext(), "download", Md5.encode(downloadInfo2.getUrl()) + ".apk"));
                    downloadInfo2.setRange(true);
                    downloadBinder.startDownload(downloadInfo2);
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
