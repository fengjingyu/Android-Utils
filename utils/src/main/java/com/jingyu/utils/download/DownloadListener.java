package com.jingyu.utils.download;

import java.io.File;

/**
 * Created by jingyu on 2017/3/5.
 */

public interface DownloadListener {

    void onPreDownload();

    void onDownloadSuccess(File file);

    void onDownloadProgress(long totalProgress, long contentLength, int progressPercent);

    void onDownloadFail(File file);

    void onDownloadPaused(File file);

    void onDownloadCanceled(File file);
}

