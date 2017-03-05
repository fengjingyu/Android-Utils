package com.jingyu.test.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.jingyu.utils.R;
import com.jingyu.utils.function.download.DownloadListener;
import com.jingyu.utils.function.download.DownloadOptions;
import com.jingyu.utils.function.download.DownloadTask;

import java.io.File;

public class DownloadService extends Service {

    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onPreDownload() {
            startForeground(1, getNotiication("下载中...", 0));
        }

        @Override
        public void onDownloadSuccess(File file) {
            stopForeground(true);
            getNotificationManager().notify(1, getNotiication("下载成功", -1));
        }

        @Override
        public void onDownloadProgress(long totalProgress, long contentLength, double downloadPercent) {
            getNotificationManager().notify(1, getNotiication("下载中..", (int) downloadPercent));
        }

        @Override
        public void onDownloadFail(File file) {
            stopForeground(true);
            getNotificationManager().notify(1, getNotiication("下载失败", -1));
        }

        @Override
        public void onDownloadPaused(File file) {
        }

        @Override
        public void onDownloadCanceled(File file) {
            if (file != null && file.exists()) {
                file.delete();
            }
            stopForeground(true);
        }
    };

    public class DownloadBinder extends Binder {

        DownloadTask downloadTask;

        public void startDownload(DownloadOptions downloadOptions) {
            if (downloadTask == null || !downloadTask.isRunning()) {
                downloadTask = new DownloadTask(downloadListener, downloadOptions);
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
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}
