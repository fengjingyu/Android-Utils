package com.jingyu.utils.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.function.IOHelper;
import com.jingyu.utils.util.UtilCollections;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.IHttp.RespHandler;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.http.RespType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author fengjingyu@foxmail.com
 * @description 该库的回调在子线程中的
 */
public class OkRespHandler<T> implements Callback {
    /**
     * 可查看如url 返回的json等
     */
    public static final String TAG_HTTP = "http";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ReqInfo reqInfo;
    private RespHandler<T> respHandler;
    private Interceptor interceptor;

    public static final String LINE = "---";

    public OkRespHandler(ReqInfo reqInfo, RespHandler<T> respHandler, Interceptor interceptor) {
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

            Logger.d(TAG_HTTP, this + LINE + "onFailure--->status code " + respInfo.getHttpCode() + "----e.toString()" + respInfo.getThrowable());
            respInfo.getThrowable().printStackTrace();
            printHeaderInfo(respInfo.getRespHeaders());

            // 是否拦截resp
            if (interceptRespCome(respInfo)) {
                return;
            }
            // 回调到uithread
            handleFailOnUiThread(respInfo);
        } else {
            Logger.d(TAG_HTTP, "onFailure--->respHandler" + LINE + reqInfo);
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

            Logger.d(TAG_HTTP, this + LINE + "onSuccess----->status code " + respInfo.getHttpCode());
            // 打印头信息
            printHeaderInfo(respInfo.getRespHeaders());
            // 是否拦截或修改原始的resp
            if (interceptRespCome(respInfo)) {
                return;
            }
            // 解析数据
            T resultBean = parse(respInfo);
            // 回调到uithread
            handleSuccessOnUiThread(resultBean, respInfo);
        } else {
            Logger.d(TAG_HTTP, "onSuccess--->respHandler为空" + LINE + reqInfo);
        }

    }

    private Map<String, List<String>> headers2Map(Headers headers) {
        return headers.toMultimap();
    }

    protected void handleFailOnUiThread(final RespInfo respInfo) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    respHandler.onFailure(reqInfo, respInfo);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Logger.e(reqInfo.getUrl() + LINE + "failure（） 异常了", e1);
                } finally {
                    try {
                        end(respInfo);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Logger.e(reqInfo.getUrl() + LINE + "failure--->end（） 异常了", e1);
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
                    Logger.e(reqInfo.getUrl() + LINE + "success（） 异常了", e);
                } finally {
                    try {
                        end(respInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e(reqInfo.getUrl() + LINE + "success--->end（） 异常了", e);
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

            Logger.d(TAG_HTTP, this + IOHelper.LINE_SEPARATOR + reqInfo + IOHelper.LINE_SEPARATOR);

            Logger.logFormatContent(TAG_HTTP, "", respInfo.getDataString());

            // 如果解析失败一定得返回null或者crash
            T resultBean = respHandler.onParse2Model(reqInfo, respInfo);

            if (resultBean == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e("解析数据失败" + LINE + this + LINE + reqInfo + LINE + respInfo.getDataString());
                    }
                });
            }

            return resultBean;

        } catch (final Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Logger.e("解析数据异常" + LINE + this + LINE + reqInfo + LINE + respInfo.getDataString(), e);
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
