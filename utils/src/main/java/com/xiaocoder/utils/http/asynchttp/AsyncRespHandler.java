package com.xiaocoder.utils.http.asynchttp;

import android.os.Handler;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.function.helper.ExecutorManager;
import com.xiaocoder.utils.function.helper.LogHelper;
import com.xiaocoder.utils.http.IHttp.Interceptor;
import com.xiaocoder.utils.http.IHttp.RespHandler;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.http.RespType;
import com.xiaocoder.utils.util.UtilCollections;
import com.xiaocoder.utils.util.UtilIo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


/**
 * @email fengjingyu@foxmail.com
 * @description 用的是android-async-http库实现的(1.4.9不在用系统的httpClient库了)
 * 该库的回调是在主线程中的，这里开启了一个子线程
 */
public class AsyncRespHandler<T> extends AsyncHttpResponseHandler {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ReqInfo reqInfo;
    private RespHandler<T> respHandler;
    private Interceptor interceptor;

    public static final String LINE = "---";

    public AsyncRespHandler(ReqInfo reqInfo, RespHandler<T> respHandler, Interceptor interceptor) {
        this.reqInfo = reqInfo;
        this.respHandler = respHandler;
        this.interceptor = interceptor;
    }

    @Override
    public void onSuccess(final int httpCode, final Header[] headers, final byte[] bytes) {

        if (respHandler != null) {
            // 子线程中解析
            ExecutorManager.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    RespInfo respInfo = new RespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setRespType(RespType.SUCCESS_WAIT_TO_PARSE);
                    respInfo.setThrowable(null);

                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    LogHelper.i(Constants.TAG_HTTP, this + LINE + "onSuccess()--->status code " + respInfo.getHttpCode());
                    // 打印头信息
                    printHeaderInfo(respInfo.getRespHeaders());
                    // 是否拦截或修改原始的resp
                    if (interceptRespCome(respInfo)) {
                        return;
                    }
                    // 解析数据
                    final T resultBean = parse(respInfo);
                    // 回调到uithread
                    handleSuccessOnUiThread(resultBean, respInfo);
                }
            });

        } else {
            LogHelper.i(Constants.TAG_HTTP, "onSuccess--->respHandler为null" + LINE + reqInfo);
        }

    }

    @Override
    public void onFailure(final int httpCode, final Header[] headers, final byte[] bytes, final Throwable throwable) {

        if (respHandler != null) {
            // 在子线程中执行
            ExecutorManager.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    RespInfo respInfo = new RespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    respInfo.setRespType(RespType.FAILURE);
                    respInfo.setThrowable(throwable);

                    LogHelper.i(Constants.TAG_HTTP, this + LINE + "onFailure--->status code " + respInfo.getHttpCode() + LINE + respInfo.getThrowable());
                    respInfo.getThrowable().printStackTrace();
                    printHeaderInfo(respInfo.getRespHeaders());

                    // 是否拦截resp
                    if (interceptRespCome(respInfo)) {
                        return;
                    }
                    // 回调到uithread
                    handleFailOnUiThread(respInfo);
                }
            });
        } else {
            LogHelper.i(Constants.TAG_HTTP, "onFailure--->respHandler为null" + LINE + reqInfo);
        }
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        super.onProgress(bytesWritten, totalSize);

        if (respHandler != null) {
            handleProgress(bytesWritten, totalSize, bytesWritten / (totalSize * 1.0D));
        }
    }

    private Map<String, List<String>> headers2Map(Header[] headers) {
        Map<String, List<String>> headersMap = new HashMap<>();

        if (headers != null) {
            for (Header header : headers) {
                if (header != null) {
                    headersMap.put(header.getName(), Arrays.asList(header.getValue()));
                }
            }
        }
        return headersMap;
    }

    protected void handleFailOnUiThread(final RespInfo respInfo) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    respHandler.onFailure(reqInfo, respInfo);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    LogHelper.e(reqInfo.getUrl() + LINE + "failure（） 异常了", e1);
                    LogHelper.dLongToast(true, reqInfo.getUrl() + LINE + "failure（） 异常了，框架里trycatch了,赶紧查看log。e的日志");
                } finally {
                    try {
                        end(respInfo);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        LogHelper.e(reqInfo.getUrl() + LINE + "failure--->end（） 异常了", e1);
                        LogHelper.dLongToast(true, reqInfo.getUrl() + LINE + "failure--->end（） 异常，框架里trycatch了,赶紧查看log日志");
                    }
                }
            }
        });
    }

    protected void handleSuccessOnUiThread(final T resultBean, final RespInfo respInfo) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (resultBean != null) {
                        // http请求成功，解析成功
                        // 接下来判断项目状态码
                        if (respHandler.onMatchAppStatusCode(reqInfo, respInfo, resultBean)) {
                            // 项目的该接口的状态码正确
                            respInfo.setRespType(RespType.SUCCESS_ALL);
                            respHandler.onSuccessAll(reqInfo, respInfo, resultBean);
                        } else {
                            // 项目的该接口的状态码有误
                            respInfo.setRespType(RespType.SUCCESS_BUT_CODE_WRONG);
                            respHandler.onSuccessButCodeWrong(reqInfo, respInfo, resultBean);
                        }
                    } else {
                        // http请求成功，但是解析失败或者没解析
                        respInfo.setRespType(RespType.SUCCESS_BUT_PARSE_WRONG);
                        respHandler.onSuccessButParseWrong(reqInfo, respInfo);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogHelper.e(reqInfo.getUrl() + LINE + "success（） 异常了", e);
                    LogHelper.dLongToast(true, reqInfo.getUrl() + LINE + "success（） 异常了，框架里trycatch了,赶紧查看log的日志");
                } finally {
                    try {
                        end(respInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogHelper.e(reqInfo.getUrl() + LINE + "success--->end（） 异常了", e);
                        LogHelper.dLongToast(true, reqInfo.getUrl() + LINE + "success--->end（） 异常了，框架里trycatch了,赶紧查看log.e的日志");
                    }
                }
            }
        });
    }

    protected void printHeaderInfo(Map<String, List<String>> headers) {
        if (LogHelper.isOutput && headers != null) {
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {

                List<String> values = header.getValue();

                if (UtilCollections.isListAvaliable(values)) {
                    LogHelper.i(Constants.TAG_HTTP, "headers--->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
                }
            }
        }
    }

    protected void end(RespInfo respInfo) {
        if (respHandler != null) {
            respHandler.onEnd(reqInfo, respInfo);
        }

        if (interceptor != null) {
            interceptor.interceptRespDone(reqInfo, respInfo);
        }
    }

    protected T parse(final RespInfo respInfo) {
        try {

            LogHelper.i(Constants.TAG_HTTP, this + LINE + UtilIo.LINE_SEPARATOR + reqInfo + UtilIo.LINE_SEPARATOR);

            LogHelper.logFormatContent(Constants.TAG_HTTP, "", respInfo.getDataString());

            // 如果解析失败一定得返回null或者crash
            T resultBean = respHandler.onParse2Model(reqInfo, respInfo);

            if (resultBean == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogHelper.e("解析数据失败" + LINE + this + LINE + reqInfo + LINE + respInfo.getDataString());
                        LogHelper.dLongToast(true, "数据解析失败，详情请查看本地日志" + respInfo.getDataString());
                    }
                });
            }

            return resultBean;

        } catch (final Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    LogHelper.e("解析数据异常" + LINE + this + LINE + e + LINE + reqInfo + LINE + respInfo.getDataString());
                    LogHelper.dLongToast(true, "数据解析异常，详情请查看本地日志" + LINE + respInfo.getDataString());
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

    private boolean interceptRespCome(RespInfo respInfo) {
        if (interceptor != null && interceptor.interceptRespCome(reqInfo, respInfo)) {
            return true;
        }
        return false;
    }
}
