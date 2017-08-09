package com.jingyu.utils.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.jingyu.utils.R;

import java.io.File;

public class DownloadService extends Service {

    public static final String APP = "demoV2.0.1";

    public static final String ACTION_PROGRESS = "ACTION_PROGRESS";
    public static final String ACTION_SUCCESS = "ACTION_SUCCESS";
    public static final String ACTION_FAIL = "ACTION_FAIL";
    public static final String KEY_PROGRESS_PERCENT = "KEY_PROGRESS_PERCENT";

    public static final int id = 1;

    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onPreDownload() {
            startForeground(id, getNotiication(APP + "下载中...", 0));
        }

        @Override
        public void onDownloadSuccess(File file) {
            stopForeground(true);

            Intent intent = new Intent();
            intent.setAction(ACTION_SUCCESS);
            sendBroadcast(intent);

            getNotificationManager().notify(id, getNotiication(APP + "下载成功", -1));
        }

        @Override
        public void onDownloadProgress(long totalProgress, long contentLength, int progressPercent) {
            Intent intent = new Intent();
            intent.setAction(ACTION_PROGRESS);
            intent.putExtra(KEY_PROGRESS_PERCENT, progressPercent);
            sendBroadcast(intent);

            getNotificationManager().notify(id, getNotiication(APP + "下载中..", progressPercent));
        }

        @Override
        public void onDownloadFail(File file) {
            stopForeground(true);

            Intent intent = new Intent();
            intent.setAction(ACTION_FAIL);
            sendBroadcast(intent);

            getNotificationManager().notify(id, getNotiication(APP + "下载失败", -1));
        }

        @Override
        public void onDownloadPaused(File file) {
        }

        @Override
        public void onDownloadCanceled(File file) {
            if (file != null && file.exists()) {
                file.delete();
            }
            getNotificationManager().cancel(id);
            stopForeground(true);
        }
    };

    public class DownloadBinder extends Binder {

        DownloadTask downloadTask;

        public void startDownload(DownloadInfo downloadInfo) {
            if (downloadTask == null || !downloadTask.isRunning()) {
                downloadTask = new DownloadTask(downloadListener, downloadInfo);
                downloadTask.execute();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null && downloadTask.isRunning()) {
                downloadTask.pauseDownload();
            }
        }

        public void cancleDownload() {
            if (downloadTask != null && downloadTask.isRunning()) {
                downloadTask.cancelDownload();
            }
        }
    }


    private Binder binder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    protected NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    protected Notification getNotiication(String title, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("开始下载");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText("下载进度" + progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
