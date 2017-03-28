package com.jingyu.middle;


import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.HttpClient;
import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.http.IHttp.RespHandler;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.ReqType;
import com.jingyu.utils.util.UtilSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class Http {
    private Http() {
    }

    private static HttpClient httpClient;

    public static void initHttp(HttpClient client) {
        httpClient = client;
    }

    public static ReqInfo http(ReqInfo reqInfo, RespHandler respHandler, Interceptor interceptor) {
        httpClient.http(reqInfo, respHandler, interceptor);
        return reqInfo;
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, boolean isSecretParam, boolean isShowDialog, RespHandler respHandler, Interceptor interceptor) {
        return common(url, ReqType.GET, paramsMap, isSecretParam, isShowDialog, respHandler, interceptor);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, Interceptor interceptor) {
        return common(url, ReqType.GET, paramsMap, true, true, respHandler, interceptor);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(url, ReqType.GET, paramsMap, true, true, respHandler, null);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, boolean isSecretParam, boolean isShowDialog, RespHandler respHandler, Interceptor interceptor) {
        return common(url, ReqType.POST, paramsMap, isSecretParam, isShowDialog, respHandler, interceptor);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, Interceptor interceptor) {
        return common(url, ReqType.POST, paramsMap, true, true, respHandler, interceptor);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(url, ReqType.POST, paramsMap, true, true, respHandler, null);
    }

    /**
     * 封装请求model
     */
    private static ReqInfo common(String url, ReqType type, Map<String, Object> paramsMap, boolean isSecretParam, boolean isShowDialog, RespHandler respHandler, Interceptor interceptor) {
        ReqInfo reqInfo = new ReqInfo();

        reqInfo.setReqType(type);
        reqInfo.setUrl(url);
        reqInfo.setHeadersMap(getHeaders(reqInfo));
        reqInfo.setParamsMap(getParams(reqInfo, isSecretParam, paramsMap));
        reqInfo.setShowDialog(isShowDialog);

        return http(reqInfo, respHandler, interceptor);
    }

    /**
     * 配置请求头
     *
     * @param reqInfo 标识,可为null
     */
    public static Map<String, List<String>> getHeaders(ReqInfo reqInfo) {
        //TODO 设置请求头
        Map<String, List<String>> map = new HashMap<>();
        map.put("_v", Arrays.asList(UtilSystem.getVersionCode(App.getApplication()) + ""));
        map.put("_p", Arrays.asList("1"));
        return map;
    }

    /**
     * 配置加密规则
     *
     * @param reqInfo 标识,可为null
     */
    public static Map<String, Object> getParams(ReqInfo reqInfo, boolean isSecretParam, Map<String, Object> paramsMap) {
        //TODO 设置加密参数
        if (isSecretParam) {
            Logger.d("加密前参数--" + paramsMap);
            paramsMap.put("testKey0", "java");
            paramsMap.put("testKey3", "c");
            paramsMap.put("testKey6", "c++");
            Logger.d("加密后参数--" + paramsMap);
        } else {
            Logger.d("请求参数未加密--" + paramsMap);
        }
        return paramsMap;
    }
}


