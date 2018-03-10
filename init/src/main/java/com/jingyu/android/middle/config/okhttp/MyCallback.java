package com.jingyu.android.middle.config.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.jingyu.android.basictools.log.Logger;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.middle.config.okhttp.resp.MyRespHandler;
import com.jingyu.android.middle.config.okhttp.resp.MyRespInfo;
import com.jingyu.android.middle.config.okhttp.resp.MyRespType;
import com.jingyu.java.mytool.basic.file.FileCreater;
import com.jingyu.java.mytool.basic.util.CollectionsUtil;
import com.jingyu.java.mytool.basic.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyCallback<T> implements Callback {
    /**
     * 可查看如url 返回的json等
     */
    public static final String TAG_HTTP = "http";
    public static final String LINE = "@@@@@@";

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private MyRespInfo myRespInfo = new MyRespInfo();
    private MyReqInfo myReqInfo;
    private MyRespHandler<T> myRespHandler;
    private MyInterceptor myInterceptor;

    public MyCallback(MyReqInfo myReqInfo, MyRespHandler<T> myRespHandler, MyInterceptor myInterceptor) {
        this.myReqInfo = myReqInfo;
        this.myRespHandler = myRespHandler;
        this.myInterceptor = myInterceptor;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        myRespInfo.setMyRespType(MyRespType.FAILURE);
        myRespInfo.setThrowable(e);
        myRespInfo.setHttpCode(0);

        Logger.d(TAG_HTTP, this + LINE + "onFailure--->status code " + myRespInfo.getHttpCode() + ",e--->" + myRespInfo.getThrowable());
        e.printStackTrace();

        handleFailOnUiThread();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        myRespInfo.setHttpCode(response.code());
        myRespInfo.setRespHeaders(headers2Map(response.headers()));
        myRespInfo.setThrowable(null);
        myRespInfo.setMyRespType(MyRespType.SUCCESS_WAITING_PARSE);

        Logger.d(TAG_HTTP, this + LINE + "onSuccess----->status code " + myRespInfo.getHttpCode());
        printHeaderInfo(myRespInfo.getRespHeaders());

        if (myRespHandler != null) {
            InputStream inputStream = response.body().byteStream();
            if (myReqInfo.isDownload()) {
                // 子线程下载
                myRespHandler.onSuccessForDownload(myReqInfo, myRespInfo, inputStream);
                handleSuccessOnUiThread(null, true);
            } else {
                // 只能读一次，否则异常
                // byte[] bytes = response.body().bytes();
                byte[] bytes = IOUtil.getBytes(inputStream);
                myRespInfo.setDataBytes(bytes);
                myRespInfo.setDataString(bytes);
                // 子线程解析
                handleSuccessOnUiThread(parse(), false);
            }
        } else {
            handleSuccessOnUiThread(null, false);
        }
    }

    private Map<String, List<String>> headers2Map(Headers headers) {
        return headers.toMultimap();
    }

    protected void handleFailOnUiThread() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (myRespHandler != null) {
                        myRespHandler.onFailure(myReqInfo, myRespInfo);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Logger.e(TAG_HTTP, myReqInfo.getUrl() + LINE + "failure（） 异常了", e1);
                } finally {
                    try {
                        end();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Logger.e(TAG_HTTP, myReqInfo.getUrl() + LINE + "failure--->end（） 异常了", e1);
                    }
                }
            }
        });
    }

    protected void handleSuccessOnUiThread(final T resultBean, final boolean isDownload) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (myRespHandler != null && !isDownload) {
                        if (resultBean != null) {

                            // http请求成功，解析成功，接下来判断状态码
                            if (myRespHandler.onMatchAppStatusCode(myReqInfo, myRespInfo, resultBean)) {
                                myRespInfo.setMyRespType(MyRespType.SUCCESS_ALL);
                                // 项目的该接口的状态码正确
                                myRespHandler.onSuccessAll(myReqInfo, myRespInfo, resultBean);
                            } else {
                                // http请求成功，解析成功，项目的该接口的状态码有误
                                myRespInfo.setMyRespType(MyRespType.SUCCESS_BUT_CODE_WRONG);
                                myRespHandler.onSuccessButCodeWrong(myReqInfo, myRespInfo, resultBean);
                            }
                        } else {
                            // http请求成功，但是解析失败或者没解析
                            myRespInfo.setMyRespType(MyRespType.SUCCESS_BUT_PARSE_WRONG);
                            myRespHandler.onSuccessButParseWrong(myReqInfo, myRespInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e(TAG_HTTP, myReqInfo.getUrl() + LINE + "success（） 异常了", e);
                } finally {
                    try {
                        end();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e(TAG_HTTP, myReqInfo.getUrl() + LINE + "success--->end（） 异常了", e);
                    }
                }
            }
        });
    }

    protected void printHeaderInfo(Map<String, List<String>> headers) {
        if (Logger.getOptions().consoleLogLevel <= Logger.INFO && headers != null) {
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {

                List<String> values = header.getValue();

                if (CollectionsUtil.isListAvaliable(values)) {
                    Logger.d(TAG_HTTP, "headers--->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
                }
            }
        }
    }

    protected void end() {
        if (myRespHandler != null) {
            myRespHandler.onEnd(myReqInfo, myRespInfo);
        }

        if (myInterceptor != null) {
            myInterceptor.interceptRespEnd(myReqInfo, myRespInfo);
        }
    }

    protected T parse() {
        try {

            Logger.d(TAG_HTTP, this + FileCreater.LINE_SEPARATOR + myReqInfo + FileCreater.LINE_SEPARATOR);
            Logger.logFormatContent(TAG_HTTP, "", myRespInfo.getDataString());

            // 如果解析失败一定得返回null或者crash
            T resultBean = myRespHandler.onParse2Model(myReqInfo, myRespInfo);

            if (resultBean == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(TAG_HTTP, "解析数据失败" + LINE + this + LINE + myReqInfo + LINE + myRespInfo.getDataString(), null);
                    }
                });
            }

            return resultBean;

        } catch (final Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Logger.e(TAG_HTTP, "解析数据异常" + LINE + this + LINE + myReqInfo + LINE + myRespInfo.getDataString(), e);
                }
            });
            return null;
        }
    }

    public void handleProgress(long bytesWritten, long totalSize, double percent) {
        if (myRespHandler != null) {
            myRespHandler.onProgressing(myReqInfo, bytesWritten, totalSize, percent);
        }
    }
}
