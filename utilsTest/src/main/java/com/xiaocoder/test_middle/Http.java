package com.xiaocoder.test_middle;


import com.xiaocoder.utils.http.IHttp.HttpClient;
import com.xiaocoder.utils.http.IHttp.Interceptor;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.ReqType;
import com.xiaocoder.utils.http.IHttp.RespHandler;

import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Http {

    private static HttpClient httpClient;

    public static void initHttp(HttpClient client) {
        httpClient = client;
    }

    public static ReqInfo http(ReqInfo reqInfo, RespHandler respHandler, Interceptor interceptor) {
        httpClient.http(reqInfo, respHandler, interceptor);
        return reqInfo;
    }

    public static ReqInfo get(String url, Map<String, Object> originParamsMap, boolean isSecretParam,
                              boolean isShowDialog, RespHandler respHandler, Interceptor interceptor) {

        return common(url, ReqType.GET, originParamsMap, isSecretParam, isShowDialog, respHandler, interceptor);
    }

    public static ReqInfo get(String url, Map<String, Object> originParamsMap, RespHandler respHandler, Interceptor interceptor) {
        return common(url, ReqType.GET, originParamsMap, true, true, respHandler, interceptor);
    }

    public static ReqInfo get(String url, Map<String, Object> originParamsMap, RespHandler respHandler) {
        return common(url, ReqType.GET, originParamsMap, true, true, respHandler, null);
    }

    public static ReqInfo post(String url, Map<String, Object> originParamsMap, boolean isSecretParam,
                               boolean isShowDialog, RespHandler respHandler, Interceptor interceptor) {

        return common(url, ReqType.POST, originParamsMap, isSecretParam, isShowDialog, respHandler, interceptor);
    }

    public static ReqInfo post(String url, Map<String, Object> originParamsMap, RespHandler respHandler, Interceptor interceptor) {
        return common(url, ReqType.POST, originParamsMap, true, true, respHandler, interceptor);
    }

    public static ReqInfo post(String url, Map<String, Object> originParamsMap, RespHandler respHandler) {
        return common(url, ReqType.POST, originParamsMap, true, true, respHandler, null);
    }

    /**
     * 封装请求model
     */
    private static ReqInfo common(String url, ReqType type, Map<String, Object> originParamsMap,
                                  boolean isSecretParam, boolean isShowDialog, RespHandler respHandler, Interceptor interceptor) {
        ReqInfo reqInfo = new ReqInfo();

        reqInfo.setReqType(type);
        reqInfo.setUrl(url);
        reqInfo.setOriginParamsMap(originParamsMap);
        reqInfo.setSecretParam(isSecretParam);
        reqInfo.setShowDialog(isShowDialog);

        return http(reqInfo, respHandler, interceptor);
    }
}


