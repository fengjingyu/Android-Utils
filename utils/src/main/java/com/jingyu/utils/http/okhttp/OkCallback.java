package com.jingyu.utils.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.http.IHttp.RespHandler;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.http.RespType;
import com.jingyu.utils.util.UtilCollections;
import com.jingyu.utils.util.UtilIo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author fengjingyu@foxmail.com
 */
public class OkCallback<T> implements Callback {
    /**
     * 可查看如url 返回的json等
     */
    public static final String TAG_HTTP = "http";
    public static final String LINE = "@@@@@@";

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private RespInfo respInfo = new RespInfo();
    private ReqInfo reqInfo;
    private RespHandler<T> respHandler;
    private Interceptor interceptor;

    public OkCallback(ReqInfo reqInfo, RespHandler<T> respHandler, Interceptor interceptor) {
        this.reqInfo = reqInfo;
        this.respHandler = respHandler;
        this.interceptor = interceptor;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        respInfo.setRespType(RespType.FAILURE);
        respInfo.setThrowable(e);
        respInfo.setHttpCode(0);

        Logger.d(TAG_HTTP, this + LINE + "onFailure--->status code " + respInfo.getHttpCode() + ",e--->" + respInfo.getThrowable());
        e.printStackTrace();

        handleFailOnUiThread();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        respInfo.setHttpCode(response.code());
        respInfo.setRespHeaders(headers2Map(response.headers()));
        respInfo.setThrowable(null);
        respInfo.setRespType(RespType.SUCCESS);

        Logger.d(TAG_HTTP, this + LINE + "onSuccess----->status code " + respInfo.getHttpCode());
        printHeaderInfo(respInfo.getRespHeaders());

        if (respHandler != null) {
            InputStream inputStream = response.body().byteStream();
            if (respHandler.isDownload()) {
                // 子线程下载
                respHandler.onSuccessForDownload(reqInfo, respInfo, inputStream);
                handleSuccessOnUiThread(null, true);
            } else {
                // 只能读一次，否则异常
                // byte[] bytes = response.body().bytes();
                byte[] bytes = UtilIo.getBytes(inputStream);
                respInfo.setDataBytes(bytes);
                respInfo.setDataString(bytes);
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
                    if (respHandler != null) {
                        respHandler.onFailure(reqInfo, respInfo);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Logger.e(TAG_HTTP, reqInfo.getUrl() + LINE + "failure（） 异常了", e1);
                } finally {
                    try {
                        end();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Logger.e(TAG_HTTP, reqInfo.getUrl() + LINE + "failure--->end（） 异常了", e1);
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
                    if (respHandler != null && !isDownload) {
                        if (resultBean != null) {

                            // http请求成功，解析成功，接下来判断状态码
                            if (respHandler.onMatchAppStatusCode(reqInfo, respInfo, resultBean)) {
                                respInfo.setRespType(RespType.SUCCESS_ALL);
                                // 项目的该接口的状态码正确
                                respHandler.onSuccessAll(reqInfo, respInfo, resultBean);
                            } else {
                                // http请求成功，解析成功，项目的该接口的状态码有误
                                respInfo.setRespType(RespType.SUCCESS_BUT_CODE_WRONG);
                                respHandler.onSuccessButCodeWrong(reqInfo, respInfo, resultBean);
                            }
                        } else {
                            // http请求成功，但是解析失败或者没解析
                            respInfo.setRespType(RespType.SUCCESS_BUT_PARSE_WRONG);
                            respHandler.onSuccessButParseWrong(reqInfo, respInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e(TAG_HTTP, reqInfo.getUrl() + LINE + "success（） 异常了", e);
                } finally {
                    try {
                        end();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e(TAG_HTTP, reqInfo.getUrl() + LINE + "success--->end（） 异常了", e);
                    }
                }
            }
        });
    }

    protected void printHeaderInfo(Map<String, List<String>> headers) {
        if (Logger.getOptions().consoleLogLevel <= Logger.INFO && headers != null) {
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {

                List<String> values = header.getValue();

                if (UtilCollections.isListAvaliable(values)) {
                    Logger.d(TAG_HTTP, "headers--->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
                }
            }
        }
    }

    protected void end() {
        if (respHandler != null) {
            respHandler.onEnd(reqInfo, respInfo);
        }

        if (interceptor != null) {
            interceptor.interceptRespEnd(reqInfo, respInfo);
        }
    }

    protected T parse() {
        try {

            Logger.d(TAG_HTTP, this + UtilIo.LINE_SEPARATOR + reqInfo + UtilIo.LINE_SEPARATOR);
            Logger.logFormatContent(TAG_HTTP, "", respInfo.getDataString());

            // 如果解析失败一定得返回null或者crash
            T resultBean = respHandler.onParse2Model(reqInfo, respInfo);

            if (resultBean == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(TAG_HTTP, "解析数据失败" + LINE + this + LINE + reqInfo + LINE + respInfo.getDataString(), null);
                    }
                });
            }

            return resultBean;

        } catch (final Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Logger.e(TAG_HTTP, "解析数据异常" + LINE + this + LINE + reqInfo + LINE + respInfo.getDataString(), e);
                }
            });
            return null;
        }
    }

    public void handleProgress(long bytesWritten, long totalSize, double percent) {
        if (respHandler != null) {
            respHandler.onProgressing(reqInfo, bytesWritten, totalSize, percent);
        }
    }
}
