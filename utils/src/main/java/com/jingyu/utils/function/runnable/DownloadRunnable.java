package com.jingyu.utils.function.runnable;

import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author fengjingyu@foxmail.com
 * @description 下载的runnable
 */
public class DownloadRunnable implements Runnable {

    private String tag = "DownloadRunnable";
    //下载的地址
    private String url = "";
    //保存的文件
    private File file;

    /**
     * @param url  下载的地址
     * @param file 存的文件
     */
    public DownloadRunnable(String url, File file) {
        this.url = url;
        this.file = file;
    }

    public interface DownloadListener {

        /**
         * @param file 下载完成的文件
         */
        void downloadFinished(File file);

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
        void downloadFail(File file);
    }

    private DownloadListener downloadListener;

    public void setDownloadListener(DownloadListener listener) {
        this.downloadListener = listener;
    }

    @Override
    public void run() {
        boolean result = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(Constants.GET);
            conn.setConnectTimeout(10000);
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                result = toFileByInputStream(conn.getInputStream(), conn.getContentLength(), downloadListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.i(tag, "下载异常");
        } finally {
            if (downloadListener != null) {
                if (result) {
                    downloadListener.downloadFinished(file);
                } else {
                    downloadListener.downloadFail(file);
                }
            }
        }
    }

    public boolean toFileByInputStream(InputStream inputStream, long totalSize, DownloadListener listener) {
        if (totalSize < 0) {
            Logger.i(tag, "conn.getContentLength()<0");
            return false;
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[10240];
            int len = -1;
            long totalProgress = 0;
            if (listener != null) {
                listener.downloadStart(totalSize, file);
            }
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                if (listener != null) {
                    totalProgress = totalProgress + len;
                    listener.downloadProgress(len, totalProgress, totalSize, file);
                }
            }
            inputStream.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
