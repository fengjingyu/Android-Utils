package com.xiaocoder.test_middle;


import com.xiaocoder.utils.http.IHttp.IHttpClient;
import com.xiaocoder.utils.http.IHttp.IRespHandler;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.ReqType;

import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Http {

    private static IHttpClient httpClient;

    public static IHttpClient getHttpClient() {
        return httpClient;
    }

    public static void initHttp(IHttpClient client) {
        httpClient = client;
    }

    public static ReqInfo http(ReqInfo reqInfo, IRespHandler respHandler) {
        httpClient.http(reqInfo, respHandler);
        return reqInfo;
    }

    public static ReqInfo get(String url, Map<String, Object> originParamsMap, boolean isSecretParam,
                              boolean isShowDialog, IRespHandler respHandler) {

        return common(url, ReqType.GET, originParamsMap, isSecretParam, isShowDialog, respHandler);
    }

    public static ReqInfo get(String url, Map<String, Object> originParamsMap, IRespHandler respHandler) {
        return common(url, ReqType.GET, originParamsMap, true, true, respHandler);
    }

    public static ReqInfo post(String url, Map<String, Object> originParamsMap, boolean isSecretParam,
                               boolean isShowDialog, IRespHandler respHandler) {

        return common(url, ReqType.POST, originParamsMap, isSecretParam, isShowDialog, respHandler);
    }

    public static ReqInfo post(String url, Map<String, Object> originParamsMap, IRespHandler respHandler) {
        return common(url, ReqType.POST, originParamsMap, true, true, respHandler);
    }

    /**
     * 封装请求model
     */
    private static ReqInfo common(String url, ReqType type, Map<String, Object> originParamsMap, boolean isSecretParam, boolean isShowDialog, IRespHandler respHandler) {
        ReqInfo reqInfo = new ReqInfo();

        reqInfo.setReqType(type);
        reqInfo.setUrl(url);
        reqInfo.setOriginParamsMap(originParamsMap);
        reqInfo.setSecretParam(isSecretParam);
        reqInfo.setShowDialog(isShowDialog);

        return http(reqInfo, respHandler);
    }
}


