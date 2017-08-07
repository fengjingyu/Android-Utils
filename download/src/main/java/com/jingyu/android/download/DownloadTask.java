package com.jingyu.android.download;

import android.os.AsyncTask;
import android.os.Build;

import com.jingyu.utils.function.DirHelper;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jingyu on 2017/3/5.
 */
public class DownloadTask extends AsyncTask<Void, Long, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILE = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private DownloadOptions options;

    private boolean isPaused = false;

    private boolean isCanceled = false;

    private boolean isRunning = false;

    public DownloadTask(DownloadListener downloadListener, DownloadOptions downloadOptions) {
        this.listener = downloadListener;
        this.options = downloadOptions;
    }

    protected boolean isParamsAvaliable() {
        if (options != null) {
            if (options.getUrl() != null && options.getUrl().trim().length() > 0) {
                if (options.getFile() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        isRunning = true;
        if (listener != null) {
            listener.onPreDownload();
        }
    }

    protected long requestContentLength() {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(options.getUrl()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            if (HttpURLConnection.HTTP_OK == urlConnection.getResponseCode()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return urlConnection.getContentLengthLong();
                } else {
                    return (long) urlConnection.getContentLength();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    protected InputStream requestInputStream(long rangePosition) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(options.getUrl()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestProperty("Range", "bytes=" + rangePosition + "-");
            if (HttpURLConnection.HTTP_PARTIAL == urlConnection.getResponseCode()) {
                return urlConnection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 防止更新的频率太快,影响ui线程的运行
    private int beforeProgressPercent;

    @Override
    protected Integer doInBackground(Void[] params) {
        if (isParamsAvaliable()) {
            BufferedInputStream bufferedInputStream = null;
            RandomAccessFile randomAccessFile = null;
            long contentLength;
            long rangePosition;
            try {
                contentLength = requestContentLength();
                if (contentLength <= 0) {
                    return TYPE_FAILE;
                } else if (contentLength == (options.getFile().exists() ? options.getFile().length() : 0)) {
                    return TYPE_SUCCESS;
                } else {
                    if (options.isRangeDownload()) {
                        if (DirHelper.createFile(options.getFile()) == null) {
                            return TYPE_FAILE;
                        }
                    } else {
                        if (DirHelper.deleteAndCreateFile(options.getFile()) == null) {
                            return TYPE_FAILE;
                        }
                    }

                    rangePosition = options.getFile().length();
                    InputStream inputStream = requestInputStream(rangePosition);
                    if (inputStream != null) {
                        bufferedInputStream = new BufferedInputStream(inputStream);
                        randomAccessFile = new RandomAccessFile(options.getFile(), "rw");
                        randomAccessFile.seek(rangePosition);
                        byte[] buf = new byte[1024];
                        long totalProgress = rangePosition;
                        for (int len; (len = bufferedInputStream.read(buf)) != -1; ) {
                            if (isCanceled) {
                                return TYPE_CANCELED;
                            } else if (isPaused) {
                                return TYPE_PAUSED;
                            } else {
                                randomAccessFile.write(buf, 0, len);
                                totalProgress = totalProgress + len;
                                int progressPercent = (int) (totalProgress * 100 / contentLength);
                                if (progressPercent > beforeProgressPercent) {
                                    publishProgress(totalProgress, contentLength, (long) progressPercent);
                                    beforeProgressPercent = progressPercent;
                                }
                            }
                        }
                        if (totalProgress == contentLength) {
                            return TYPE_SUCCESS;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return TYPE_FAILE;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        if (listener != null) {
            long totalProgress = values[0];
            long contentLength = values[1];
            long progressPercent = values[2];
            listener.onDownloadProgress(totalProgress, contentLength, (int) progressPercent);
        }
    }

    @Override
    protected void onPostExecute(Integer resultType) {
        isRunning = false;
        if (listener != null) {
            switch (resultType) {
                case TYPE_SUCCESS:
                    listener.onDownloadSuccess(options.getFile());
                    break;
                case TYPE_FAILE:
                    listener.onDownloadFail(options.getFile());
                    break;
                case TYPE_PAUSED:
                    listener.onDownloadPaused(options.getFile());
                    break;
                case TYPE_CANCELED:
                    listener.onDownloadCanceled(options.getFile());
                    break;
                default:
                    break;
            }
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

}