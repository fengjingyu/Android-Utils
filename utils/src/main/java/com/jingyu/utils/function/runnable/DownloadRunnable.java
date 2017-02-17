package com.jingyu.utils.function.runnable;

import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilIo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    private DownloadListener downloadListener;

    public void setDownloadListener(DownloadListener listener) {
        this.downloadListener = listener;
    }

    /**
     * @param url  下载的地址
     * @param file 存到哪个文件
     */
    public DownloadRunnable(String url, File file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {
        InputStream in = null;
        try {
            Logger.i(tag, "----进入下载的run方法");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(Constants.GET);
            conn.setConnectTimeout(10000);
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                in = conn.getInputStream();
                long totalSize = conn.getContentLength();
                if (totalSize < 0) {
                    Logger.e(this + "conn.getContentLength()<0");
                    return;
                }
                Logger.i(tag, "----开始下载了");
                UtilIo.toFileByInputStream(in, file, totalSize, downloadListener, false);
                if (downloadListener != null) {
                    Logger.i(tag, "----下载完成----" + Thread.currentThread());
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
            Logger.i(tag, "--下载excpetion---" + e.toString());
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
