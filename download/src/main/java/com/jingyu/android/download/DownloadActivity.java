package com.jingyu.android.download;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.jingyu.android.middle.Http;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.http.json.JsonModel;
import com.jingyu.android.middle.http.json.JsonRespHandler;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

public class DownloadActivity extends BaseActivity implements View.OnClickListener {

    Button startDownload;
    Button pauseDownload;
    Button cancelDownload;
    Button startRangeDownload;
    Button upgrade;

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
        upgrade = getViewById(R.id.upgrade);


        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        startRangeDownload.setOnClickListener(this);
        upgrade.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (downloadBinder != null) {
            switch (v.getId()) {
                case R.id.startDownload:
                    DownloadInfo downloadInfo = new DownloadInfo();
                    downloadInfo.setUrl("http://192.168.0.102/android/appv2.apk");
                    downloadInfo.setFile(DirHelper.ExternalAndroid.getFile(getApplicationContext(), "download", "test.apk"));
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
                    downloadInfo2.setFile(DirHelper.ExternalAndroid.getFile(getApplicationContext(), "download", "est_range.apk"));
                    downloadInfo2.setRangeDownload(true);
                    downloadBinder.startDownload(downloadInfo2);
                    break;
                case R.id.upgrade:
                    request();
                    break;
                default:
                    break;
            }
        }
    }

    public void request() {
        Http.get("http://www.baidu.com", null, new JsonRespHandler(getActivity()) {
            @Override
            public JsonModel onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {
                return new JsonModel();
            }

            @Override
            public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                return true;
            }

            @Override
            public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setUrl("http://192.168.0.102/android/appv2.apk");
                downloadInfo.setFile(DirHelper.ExternalAndroid.getFile(getApplicationContext(), "upgrade", "upgradeapp.apk"));
                downloadInfo.setRangeDownload(false);
                downloadInfo.setForceUpgrade(true);
                downloadInfo.setContent("修复bug");
                downloadInfo.setTitle("V2.0.0最新版本升级");
                UpgradeActivity.actionStart(getActivity(), downloadInfo);
            }
        });
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
