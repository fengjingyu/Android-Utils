package com.xiaocoder.utils.http.asynchttp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.xiaocoder.utils.http.IHttp.HttpClient;
import com.xiaocoder.utils.http.IHttp.Interceptor;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.IHttp.RespHandler;
import com.xiaocoder.utils.util.UtilCollections;

import java.util.List;
import java.util.Map;

/**
 * @email fengjingyu@foxmail.com
 * @description 用的是android-async-http库实现的
 */

/**
 * asyn-http-android库
 * 1 文件上传： params.put("字段", new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/0912/compass.png"));
 * 同时支持流和字节
 * <p/>
 * 2  Adding HTTP Basic Auth credentials 见github文档
 * http://loopj.com/android-async-http/
 */
public class AsyncClient implements HttpClient {
    /**
     * 网络超时,毫秒
     */
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

            httpClient.get(reqInfo.getUrl(), getRequestParams(reqInfo.getFinalRequestParamsMap()), new AsyncRespHandler(reqInfo, respHandler, interceptor));

        } else if (reqInfo.isPost()) {

            httpClient.post(reqInfo.getUrl(), getRequestParams(reqInfo.getFinalRequestParamsMap()), new AsyncRespHandler(reqInfo, respHandler, interceptor));

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
