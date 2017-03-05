package com.jingyu.utils.function.download;

import android.os.AsyncTask;
import android.os.Build;

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

    protected boolean isOptoinsAvaliable() {
        if (options != null) {
            if (options.getUrl() != null && options.getUrl().trim().length() > 0) {
                if (options.getFile() != null && options.getFile().exists()) {
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

    protected Object[] request(long downloadedLength) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(options.getUrl()).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestProperty("Range", "bytes=" + downloadedLength + "-");
            if (HttpURLConnection.HTTP_PARTIAL == urlConnection.getResponseCode()) {
                Object[] objects = new Object[2];
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    objects[0] = urlConnection.getContentLengthLong();
                } else {
                    objects[0] = (long) urlConnection.getContentLength();
                }
                objects[1] = urlConnection.getInputStream();
                return objects;
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
        return null;
    }

    @Override
    protected Integer doInBackground(Void[] params) {
        if (isOptoinsAvaliable()) {
            BufferedInputStream bufferedInputStream = null;
            RandomAccessFile randomAccessFile = null;
            long contentLength;
            long downloadedLength;
            try {
                downloadedLength = options.isRangeDownload() ? options.getFile().length() : 0;

                Object[] objects = request(downloadedLength);
                if (objects == null || !(objects[0] instanceof Long) || !(objects[1] instanceof InputStream)) {
                    return TYPE_FAILE;
                }
                contentLength = (long) objects[0];
                bufferedInputStream = new BufferedInputStream((InputStream) objects[1]);

                if (contentLength <= 0) {
                    return TYPE_FAILE;
                } else if (contentLength == downloadedLength) {
                    return TYPE_SUCCESS;
                } else {
                    randomAccessFile = new RandomAccessFile(options.getFile(), "rw");
                    randomAccessFile.seek(downloadedLength);

                    byte[] buf = new byte[1024];
                    long totalProgress = downloadedLength;
                    for (int len; (len = bufferedInputStream.read(buf)) != -1; ) {
                        if (isCanceled) {
                            return TYPE_CANCELED;
                        } else if (isPaused) {
                            return TYPE_PAUSED;
                        } else {
                            randomAccessFile.write(buf, 0, len);
                            publishProgress(totalProgress = totalProgress + len, contentLength);
                        }
                    }
                    return TYPE_SUCCESS;
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
        long totalProgress = values[0];
        long contentLength = values[1];
        if (listener != null) {
            listener.onDownloadProgress(totalProgress, contentLength, totalProgress * 1.0 / contentLength);
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
