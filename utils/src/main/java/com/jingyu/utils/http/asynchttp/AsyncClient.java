package com.jingyu.utils.http.asynchttp;

import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.util.UtilCollections;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.jingyu.utils.http.IHttp.HttpClient;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.IHttp.RespHandler;

import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * @description 用的是android-async-http库
 */

public class AsyncClient implements HttpClient {

    private static final int DEFAULT_TIME_OUT = 10000;

    private AsyncHttpClient httpClient;

    public AsyncClient() {
        init(DEFAULT_TIME_OUT);
    }

    public AsyncClient(int outTime) {
        init(outTime);
    }

    private void init(int timeOut) {
        this.httpClient = new AsyncHttpClient();
        this.httpClient.setTimeout(timeOut);
    }

    @Override
    public void http(ReqInfo reqInfo, RespHandler respHandler, Interceptor interceptor) {

        // 是否拦截请求
        if (interceptor != null && interceptor.interceptReqSend(reqInfo)) {
            return;
        }

        if (respHandler != null) {
            respHandler.onReadySendRequest(reqInfo);
        }

        // 添加请求头信息
        addRequestHeaders(reqInfo.getHeadersMap());

        if (reqInfo.isGet()) {

            httpClient.get(reqInfo.getUrl(), getRequestParams(reqInfo.getParamsMap()), new AsyncRespHandler(reqInfo, respHandler, interceptor));

        } else if (reqInfo.isPost()) {

            httpClient.post(reqInfo.getUrl(), getRequestParams(reqInfo.getParamsMap()), new AsyncRespHandler(reqInfo, respHandler, interceptor));

        } else {
            throw new RuntimeException("AsyncClient---请求类型不匹配");
        }
    }

    private void addRequestHeaders(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = entry.getValue();

            if (UtilCollections.isListAvaliable(values)) {
                httpClient.addHeader(entry.getKey(), entry.getValue().get(0));
            }
        }
    }

    /**
     * 转换参数
     */
    private RequestParams getRequestParams(Map<String, Object> secretedMap) {
        RequestParams params = new RequestParams();
        if (secretedMap != null) {
            for (Map.Entry<String, Object> item : secretedMap.entrySet()) {
                String key = item.getKey();
                Object value = item.getValue();
                params.put(key, value);
            }
        }
        return params;
    }
}
