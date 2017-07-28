package com.jingyu.android.middle;


import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.HttpClient;
import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.http.IHttp.RespHandler;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.ReqType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
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

    public static ReqInfo download(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, isShowDialog, interceptor, true);
    }

    public static ReqInfo download(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(ReqType.GET, url, paramsMap, respHandler, null, true, null, true);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, isShowDialog, interceptor, false);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, isShowDialog, null, false);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag) {
        return common(ReqType.GET, url, paramsMap, respHandler, tag, true, null, false);
    }

    public static ReqInfo get(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(ReqType.GET, url, paramsMap, respHandler, null, true, null, false);
    }

    public static ReqInfo get(String url, RespHandler respHandler) {
        return common(ReqType.GET, url, null, respHandler, null, true, null, false);
    }

    public static ReqInfo get(String url) {
        return common(ReqType.GET, url, null, null, null, true, null, false);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        return common(ReqType.POST, url, paramsMap, respHandler, tag, isShowDialog, interceptor, false);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog) {
        return common(ReqType.POST, url, paramsMap, respHandler, tag, isShowDialog, null, false);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag) {
        return common(ReqType.POST, url, paramsMap, respHandler, tag, true, null, false);
    }

    public static ReqInfo post(String url, Map<String, Object> paramsMap, RespHandler respHandler) {
        return common(ReqType.POST, url, paramsMap, respHandler, null, true, null, false);
    }

    /**
     * 封装请求model
     */
    private static ReqInfo common(ReqType type, String url, Map<String, Object> paramsMap, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor, boolean isDownload) {
        ReqInfo reqInfo = new ReqInfo();
        reqInfo.setReqType(type);
        reqInfo.setUrl(url);
        reqInfo.setShowDialog(isShowDialog);
        reqInfo.setTag(tag);
        reqInfo.setDownload(isDownload);

        reqInfo.setHeadersMap(getHeaders(tag));
        reqInfo.setParamsMap(getParams(tag, paramsMap));

        return http(reqInfo, respHandler, interceptor);
    }

    private static ReqInfo postJson(String url, String json, RespHandler respHandler, String tag, boolean isShowDialog, Interceptor interceptor) {
        ReqInfo reqInfo = new ReqInfo();
        reqInfo.setReqType(ReqType.POST);
        reqInfo.setUrl(url);
        reqInfo.setShowDialog(isShowDialog);
        reqInfo.setTag(tag);
        reqInfo.setHeadersMap(getHeaders(tag));
        reqInfo.setPostString(json);
        reqInfo.setPostStringContentType("application/json");
        return http(reqInfo, respHandler, interceptor);
    }

    public static void postJson(String url, String json, RespHandler respHandler) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            //todo
            jsonObject.put("authcode", "123456");
            jsonObject.put("plateform", "Android");
            postJson(url, jsonObject.toString(), respHandler, "", true, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void postJsonNoCommonField(String url, String json, RespHandler respHandler) {
        postJson(url, json, respHandler, "", true, null);
    }

    /**
     * 配置请求头
     *
     * @param tag 标识
     */
    private static Map<String, List<String>> getHeaders(String tag) {
        //TODO 设置请求头
        Map<String, List<String>> map = new HashMap<>();
        map.put("_p", Arrays.asList("1"));
        return map;
    }

    /**
     * 配置加密规则
     *
     * @param tag 标识
     */
    private static Map<String, Object> getParams(String tag, Map<String, Object> paramsMap) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        //TODO 设置加密参数
        Logger.d("加密前参数--" + paramsMap);
        paramsMap.put("testKey0", "java");
        paramsMap.put("testKey3", "c");
        paramsMap.put("testKey6", "c++");
        Logger.d("加密后参数--" + paramsMap);
        return paramsMap;
    }
}


