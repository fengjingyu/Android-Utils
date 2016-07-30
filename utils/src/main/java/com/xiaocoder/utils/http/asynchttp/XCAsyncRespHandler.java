package com.xiaocoder.utils.http.asynchttp;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaocoder.utils.application.XCConstant;
import com.xiaocoder.utils.function.thread.XCExecutor;
import com.xiaocoder.utils.http.XCHttpHandlerCtrl;
import com.xiaocoder.utils.http.XCRespInfo;
import com.xiaocoder.utils.http.XCRespType;
import com.xiaocoder.utils.io.XCLog;

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
public class XCAsyncRespHandler extends AsyncHttpResponseHandler {

    private XCHttpHandlerCtrl httpHandlerCtrl;

    public XCAsyncRespHandler(XCHttpHandlerCtrl httpHandlerCtrl) {
        this.httpHandlerCtrl = httpHandlerCtrl;
    }

    @Override
    public void onSuccess(final int httpCode, final Header[] headers, final byte[] bytes) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            XCExecutor.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    XCRespInfo respInfo = new XCRespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setRespType(XCRespType.SUCCESS_WAIT_TO_PARSE);
                    respInfo.setThrowable(null);

                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    httpHandlerCtrl.handlerSuccess(respInfo);
                }
            });

        } else {
            XCLog.i(XCConstant.TAG_HTTP, "onSuccess--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }

    }

    @Override
    public void onFailure(final int httpCode, final Header[] headers, final byte[] bytes, final Throwable throwable) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            XCExecutor.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    XCRespInfo respInfo = new XCRespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    respInfo.setRespType(XCRespType.FAILURE);
                    respInfo.setThrowable(throwable);

                    httpHandlerCtrl.handlerFail(respInfo);
                }
            });

        } else {
            XCLog.i(XCConstant.TAG_HTTP, "onFailure--未传入handler--" + httpHandlerCtrl.getReqInfo());
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
