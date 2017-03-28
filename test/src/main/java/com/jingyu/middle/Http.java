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

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, isShowDialog, interceptor);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, isShowDialog, null);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, true, null);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(ReqType.GET, url, paramsMap, respHandler, null, true, null);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        return common(ReqType.POST, url, paramsMap, respHandler, tag, isShowDialog, interceptor);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog) {
        return common(ReqType.POST, url, paramsMap, respHandler, tag, isShowDialog, null);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag) {
        return common(ReqType.POST, url, paramsMap, respHandler, tag, true, null);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(ReqType.POST, url, paramsMap, respHandler, null, true, null);
    }

    /**
     * 封装请求model
     */
    private static ReqInfo common(ReqType type, String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        ReqInfo reqInfo = new ReqInfo();
        reqInfo.setReqType(type);
        reqInfo.setUrl(url);
        reqInfo.setHeadersMap(getHeaders(tag));
        reqInfo.setParamsMap(getParams(tag, paramsMap));
        reqInfo.setShowDialog(isShowDialog);
        reqInfo.setTag(tag);

        return http(reqInfo, respHandler, interceptor);
    }

    /**
     * 配置请求头
     *
     * @param tag 标识
     */
    private static Map<String, List<String>> getHeaders(String tag) {
        //TODO 设置请求头
        Map<String, List<String>> map = new HashMap<>();
        map.put("_v", Arrays.asList(UtilSystem.getVersionCode(App.getApplication()) + ""));
        map.put("_p", Arrays.asList("1"));
        return map;
    }

    /**
     * 配置加密规则
     *
     * @param tag 标识
     */
    private static Map<String, Object> getParams(String tag, Map<String, Object> paramsMap) {
        //TODO 设置加密参数
        Logger.d("加密前参数--" + paramsMap);
        paramsMap.put("testKey0", "java");
        paramsMap.put("testKey3", "c");
        paramsMap.put("testKey6", "c++");
        Logger.d("加密后参数--" + paramsMap);
        return paramsMap;
    }
}


