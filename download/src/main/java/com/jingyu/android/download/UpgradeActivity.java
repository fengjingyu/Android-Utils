package com.jingyu.android.download;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;

import com.jingyu.android.middle.base.BaseActivity;

public class UpgradeActivity extends BaseActivity {
    public static final String KEY = "key_downloadinfo";
    private ProgressDialog forceDialog;
    private DownloadInfo downloadInfo;
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_PROGRESS.equals(intent.getAction())) {
                int percent = intent.getIntExtra(DownloadService.KEY_PROGRESS_PERCENT, 0);
                if (forceDialog != null) {
                    forceDialog.setProgress(percent);
                    forceDialog.setSecondaryProgress(percent - 10);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        service();

        receiver();

        upgrade();
    }

    private void upgrade() {
        downloadInfo = (DownloadInfo) getIntent().getSerializableExtra(KEY);
        if (downloadInfo != null) {
            if (downloadInfo.isForceUpgrade()) {
                // 强制升级
                forceUpgrade();
            } else {
                // 非强制升级
                choiceUpgrade();
            }
        }
    }

    private void receiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_PROGRESS);
        registerReceiver(broadcastReceiver, filter);
    }

    private void service() {
        //启动服务
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        // 绑定服务
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void forceUpgrade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(downloadInfo.getTitle());
        builder.setMessage(downloadInfo.getContent());
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (forceDialog == null) {
                    forceDialog = new ProgressDialog(getActivity());
                    forceDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    forceDialog.setMax(100);
                    forceDialog.setProgress(0);
                    forceDialog.setCancelable(false);
                    forceDialog.show();
                }
                download();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void choiceUpgrade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(downloadInfo.getTitle());
        builder.setMessage(downloadInfo.getContent());
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                download();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private void download() {
        if (downloadBinder != null) {
            downloadBinder.startDownload(downloadInfo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unbindService(serviceConnection);
    }

    public static void actionStart(Activity activity, DownloadInfo downloadInfo) {
        Intent intent = new Intent(activity, UpgradeActivity.class);
        intent.putExtra(KEY, downloadInfo);
        activity.startActivity(intent);
    }
}
