package com.xiaocoder.utils.function.runnable;

import com.xiaocoder.utils.application.Constants;
import com.xiaocoder.utils.io.LogHelper;
import com.xiaocoder.utils.util.UtilIo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @email fengjingyu@foxmail.com
 * @description 下载的runnable
 */
public class DownloadRunnable implements Runnable {

    private String tag = Constants.TAG_TEMP;
    private String url = "";
    private File file;

    public interface DownloadListener {

        /**
         * @param totalSize 服务器上的该文件的大小
         * @param file      下载完成的文件
         */
        void downloadFinished(long totalSize, File file);

        /**
         * @param len           每一次读取的大小
         * @param totalProgress 累计读取的大小
         * @param totalSize     服务器上的该文件的大小服务器上的该文件的大小
         * @param file          正在下载的文件
         */
        void downloadProgress(int len, long totalProgress, long totalSize, File file);

        /**
         * 即将开始下载，还未开始
         *
         * @param totalSize 服务器上的该文件的大小
         * @param file      下载到这个file中
         */
        void downloadStart(long totalSize, File file);

        /**
         * 还未开始下载，网络连接就失败
         * 或者是下载过程中，出现异常
         *
         * @param file
         */
        void netFail(File file);

    }

    public DownloadListener downloadListener;

    public void setDownloadListener(DownloadListener listener) {
        this.downloadListener = listener;
    }


    public DownloadRunnable(String url, File file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            LogHelper.i(tag, "----进入下载的run方法");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(Constants.GET);
            conn.setConnectTimeout(10000);
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                in = conn.getInputStream();
                long totalSize = conn.getContentLength();
                if (totalSize < 0) {
                    LogHelper.e(this + "conn.getContentLength()<0");
                    return;
                }
                LogHelper.i(tag, "----开始下载了");
                UtilIo.toFileByInputStream(in, file, totalSize, downloadListener, false);
                if (downloadListener != null) {
                    LogHelper.i(tag, "----下载完成----" + Thread.currentThread());
                    downloadListener.downloadFinished(totalSize, file);
                }
            } else {
                if (downloadListener != null) {
                    downloadListener.netFail(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (downloadListener != null) {
                downloadListener.netFail(file);
            }
            LogHelper.i(tag, "--下载excpetion---" + e.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
