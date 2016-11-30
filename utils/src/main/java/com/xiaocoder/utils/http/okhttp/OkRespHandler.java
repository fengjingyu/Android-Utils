package com.xiaocoder.utils.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.function.helper.LogHelper;
import com.xiaocoder.utils.http.Interceptor;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespHandler;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.http.RespType;
import com.xiaocoder.utils.util.UtilCollections;
import com.xiaocoder.utils.util.UtilIo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @email fengjingyu@foxmail.com
 * @description okhttp实现，该库的回调在子线程中的
 */
public class OkRespHandler implements Callback {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ReqInfo reqInfo;
    private RespHandler respHandler;
    private Interceptor interceptor;

    public OkRespHandler(ReqInfo reqInfo, RespHandler respHandler, Interceptor interceptor) {
        this.reqInfo = reqInfo;
        this.respHandler = respHandler;
        this.interceptor = interceptor;
    }

    @Override
    public void onFailure(Call call, IOException e) {

        if (respHandler != null) {

            RespInfo respInfo = new RespInfo();

            respInfo.setRespType(RespType.FAILURE);
            respInfo.setThrowable(e);

            LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onFailure()");
            LogHelper.i(Constants.TAG_HTTP, "onFailure----->status code " + respInfo.getHttpCode() + "----e.toString()" + respInfo.getThrowable());
            respInfo.getThrowable().printStackTrace();
            printHeaderInfo(respInfo.getRespHeaders());

            handlerFail(respInfo);

        } else {
            LogHelper.i(Constants.TAG_HTTP, "onFailure--respHandler--" + reqInfo);
        }

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (respHandler != null) {

            RespInfo respInfo = new RespInfo();
            respInfo.setHttpCode(response.code());
            respInfo.setRespHeaders(headers2Map(response.headers()));

            // 只能读一次，否则异常
            byte[] bytes = response.body().bytes();
            respInfo.setDataBytes(bytes);
            respInfo.setDataString(bytes);

            respInfo.setRespType(RespType.SUCCESS_WAIT_TO_PARSE);
            respInfo.setThrowable(null);

            LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccess()");
            LogHelper.i(Constants.TAG_HTTP, "onSuccess----->status code " + respInfo.getHttpCode());
            printHeaderInfo(respInfo.getRespHeaders());

            handlerSuccess(respInfo);
        } else {
            LogHelper.i(Constants.TAG_HTTP, "onSuccess--respHandler为空--" + reqInfo);
        }

    }

    private Map<String, List<String>> headers2Map(Headers headers) {
        return headers.toMultimap();
    }

    protected void handlerFail(final RespInfo respInfo) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    respHandler.onFailure(reqInfo, respInfo);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    LogHelper.e(reqInfo.getUrl() + "---failure（） 异常了", e1);
                    LogHelper.dLongToast(true, reqInfo.getUrl() + "---failure（） 异常了，框架里trycatch了,赶紧查看log。e的日志");
                } finally {
                    try {
                        httpEnd(respInfo);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        LogHelper.e(reqInfo.getUrl() + "---failure---httpEnd（） 异常了", e1);
                        LogHelper.dLongToast(true, reqInfo.getUrl() + "---failure---httpEnd（） 异常，框架里trycatch了,赶紧查看log日志");
                    }
                }
            }
        });
    }

    protected void handlerSuccess(final RespInfo respInfo) {

        final Object resultBean = parse(respInfo);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
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

                } catch (Exception e) {
                    e.printStackTrace();
                    LogHelper.e(reqInfo.getUrl() + "---success（） 异常了", e);
                    LogHelper.dLongToast(true, reqInfo.getUrl() + "---success（） 异常了，框架里trycatch了,赶紧查看log的日志");
                } finally {
                    try {
                        httpEnd(respInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogHelper.e(reqInfo.getUrl() + "---success---httpEnd（） 异常了", e);
                        LogHelper.dLongToast(true, reqInfo.getUrl() + "---success---httpEnd（） 异常了，框架里trycatch了,赶紧查看log.e的日志");
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
                    LogHelper.i(Constants.TAG_HTTP, "headers----->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
                }
            }
        }
    }

    protected void httpEnd(RespInfo respInfo) {
        if (respHandler != null) {
            respHandler.onEnd(reqInfo, respInfo);
        }

        if (interceptor != null) {
            interceptor.onRespDone(reqInfo, respInfo);
        }
    }

    protected Object parse(final RespInfo respInfo) {
        try {
            LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----parse()");

            LogHelper.i(Constants.TAG_HTTP, UtilIo.LINE_SEPARATOR + reqInfo + UtilIo.LINE_SEPARATOR);

            LogHelper.logFormatContent(Constants.TAG_HTTP, "", respInfo.getDataString());

            // 这是抽象方法，子类实现解析方式,如果解析失败一定得返回null
            Object resultBean = respHandler.onParse2Model(reqInfo, respInfo);

            if (resultBean == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogHelper.dLongToast(true, "数据解析失败，详情请查看本地日志--" + respInfo.getDataString());
                    }
                });
                LogHelper.e("解析数据失败---" + this.toString() + "---" + reqInfo + "---" + respInfo.getDataString());
            }

            return resultBean;

        } catch (Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    LogHelper.dLongToast(true, "数据解析异常，详情请查看本地日志--" + respInfo.getDataString());
                }
            });

            LogHelper.e("解析数据异常---" + this.toString() + "---" + e.toString() + "---" + reqInfo + "---" + respInfo.getDataString());
            return null;
        }
    }

    public void handlerProgress(long bytesWritten, long totalSize, double percent) {
        if (respHandler != null) {
            respHandler.onProgressing(reqInfo, bytesWritten, totalSize, percent);
        }
    }
}
