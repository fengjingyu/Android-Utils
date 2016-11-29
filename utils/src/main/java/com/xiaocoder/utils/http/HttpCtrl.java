package com.xiaocoder.utils.http;

import android.os.Handler;
import android.os.Looper;

import com.xiaocoder.utils.application.Constants;
import com.xiaocoder.utils.http.IHttp.IRespHandler;
import com.xiaocoder.utils.io.LogHelper;
import com.xiaocoder.utils.util.UtilIo;
import com.xiaocoder.utils.util.UtilCollections;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @email fengjingyu@foxmail.com
 * @description 处理http发送前后的方法回调
 */
public class HttpCtrl {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private IRespHandler respHandler;

    private ReqInfo reqInfo;

    public IRespHandler getRespHandler() {
        return respHandler;
    }

    public ReqInfo getReqInfo() {
        return reqInfo;
    }

    public HttpCtrl(ReqInfo reqInfo, IRespHandler respHandler) {
        this.reqInfo = reqInfo;
        this.respHandler = respHandler;
    }

    public boolean isIntercepte() {
        if (respHandler != null) {
            if (reqInfo.getHttpNotify() == null || reqInfo.getHttpNotify().onReadySendNotify(reqInfo)) {
                respHandler.onReadySendRequest(reqInfo);
            } else {
                // getHttpNotify().onReadySendNotify()返回false，即拦截该次网络请求
                return true;
            }
        }
        LogHelper.i(Constants.TAG_RESP_HANDLER, reqInfo);
        LogHelper.i(Constants.TAG_HTTP, reqInfo);
        return false;
    }

    public void handlerFail(RespInfo respInfo) {

        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onFailure()");
        LogHelper.i(Constants.TAG_HTTP, "onFailure----->status code " + respInfo.getHttpCode() + "----e.toString()" + respInfo.getThrowable());

        respInfo.getThrowable().printStackTrace();

        printHeaderInfo(respInfo.getRespHeaders());

        fail(respInfo);
    }

    protected void fail(final RespInfo respInfo) {

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

    public void handlerSuccess(RespInfo respInfo) {

        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccess()");
        LogHelper.i(Constants.TAG_HTTP, "onSuccess----->status code " + respInfo.getHttpCode());

        printHeaderInfo(respInfo.getRespHeaders());

        success(respInfo);

    }

    protected void success(final RespInfo respInfo) {

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

        respHandler.onEnd(reqInfo, respInfo);

        if (reqInfo.getHttpNotify() != null) {
            reqInfo.getHttpNotify().onEndNotify(reqInfo, respInfo);
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
        respHandler.onProgressing(reqInfo, bytesWritten, totalSize, percent);
    }
}
