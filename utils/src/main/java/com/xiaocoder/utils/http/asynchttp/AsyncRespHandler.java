package com.xiaocoder.utils.http.asynchttp;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaocoder.utils.application.Constants;
import com.xiaocoder.utils.function.helper.ExecutorHelper;
import com.xiaocoder.utils.http.HttpHandlerCtrl;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.http.RespType;
import com.xiaocoder.utils.io.LogHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 用的是android-async-http库实现的(1.4.9不在用系统的httpClient库了)
 * 该库的回调是在主线程中的，这里开启了一个子线程
 */
public class AsyncRespHandler extends AsyncHttpResponseHandler {

    private HttpHandlerCtrl httpHandlerCtrl;

    public AsyncRespHandler(HttpHandlerCtrl httpHandlerCtrl) {
        this.httpHandlerCtrl = httpHandlerCtrl;
    }

    @Override
    public void onSuccess(final int httpCode, final Header[] headers, final byte[] bytes) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            ExecutorHelper.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    RespInfo respInfo = new RespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setRespType(RespType.SUCCESS_WAIT_TO_PARSE);
                    respInfo.setThrowable(null);

                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    httpHandlerCtrl.handlerSuccess(respInfo);
                }
            });

        } else {
            LogHelper.i(Constants.TAG_HTTP, "onSuccess--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }

    }

    @Override
    public void onFailure(final int httpCode, final Header[] headers, final byte[] bytes, final Throwable throwable) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            ExecutorHelper.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    RespInfo respInfo = new RespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    respInfo.setRespType(RespType.FAILURE);
                    respInfo.setThrowable(throwable);

                    httpHandlerCtrl.handlerFail(respInfo);
                }
            });

        } else {
            LogHelper.i(Constants.TAG_HTTP, "onFailure--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        super.onProgress(bytesWritten, totalSize);

        if (httpHandlerCtrl.getRespHandler() != null) {

            httpHandlerCtrl.handlerProgress(bytesWritten, totalSize, bytesWritten / (totalSize * 1.0D));

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
}
