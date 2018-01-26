package com.jingyu.android.middle;


import com.jingyu.android.middle.config.okhttp.MyHttpClient;
import com.jingyu.android.middle.config.okhttp.MyInterceptor;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.middle.config.okhttp.req.MyReqType;
import com.jingyu.android.middle.config.okhttp.resp.MyRespHandler;
import com.jingyu.utils.function.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public class AppHttp {
    private AppHttp() {
    }

    private static MyHttpClient myHttpClient;

    public static void initHttp(MyHttpClient client) {
        myHttpClient = client;
    }

    public static MyReqInfo http(MyReqInfo myReqInfo, MyRespHandler myRespHandler, MyInterceptor myInterceptor) {
        myHttpClient.http(myReqInfo, myRespHandler, myInterceptor);
        return myReqInfo;
    }

    public static MyReqInfo download(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag, boolean isShowDialog, MyInterceptor myInterceptor) {
        return common(MyReqType.GET, url, paramsMap, myRespHandler, tag, isShowDialog, myInterceptor, true);
    }

    public static MyReqInfo download(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler) {
        return common(MyReqType.GET, url, paramsMap, myRespHandler, null, true, null, true);
    }

    public static MyReqInfo get(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag, boolean isShowDialog, MyInterceptor myInterceptor) {
        return common(MyReqType.GET, url, paramsMap, myRespHandler, tag, isShowDialog, myInterceptor, false);
    }

    public static MyReqInfo get(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag, boolean isShowDialog) {
        return common(MyReqType.GET, url, paramsMap, myRespHandler, tag, isShowDialog, null, false);
    }

    public static MyReqInfo get(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag) {
        return common(MyReqType.GET, url, paramsMap, myRespHandler, tag, true, null, false);
    }

    public static MyReqInfo get(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler) {
        return common(MyReqType.GET, url, paramsMap, myRespHandler, null, true, null, false);
    }

    public static MyReqInfo get(String url, MyRespHandler myRespHandler) {
        return common(MyReqType.GET, url, null, myRespHandler, null, true, null, false);
    }

    public static MyReqInfo get(String url) {
        return common(MyReqType.GET, url, null, null, null, true, null, false);
    }

    public static MyReqInfo post(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag, boolean isShowDialog, MyInterceptor myInterceptor) {
        return common(MyReqType.POST, url, paramsMap, myRespHandler, tag, isShowDialog, myInterceptor, false);
    }

    public static MyReqInfo post(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag, boolean isShowDialog) {
        return common(MyReqType.POST, url, paramsMap, myRespHandler, tag, isShowDialog, null, false);
    }

    public static MyReqInfo post(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag) {
        return common(MyReqType.POST, url, paramsMap, myRespHandler, tag, true, null, false);
    }

    public static MyReqInfo post(String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler) {
        return common(MyReqType.POST, url, paramsMap, myRespHandler, null, true, null, false);
    }

    private static MyReqInfo common(MyReqType type, String url, Map<String, Object> paramsMap, MyRespHandler myRespHandler, String tag, boolean isShowDialog, MyInterceptor myInterceptor, boolean isDownload) {
        MyReqInfo myReqInfo = new MyReqInfo();
        myReqInfo.setMyReqType(type);
        myReqInfo.setUrl(url);
        myReqInfo.setShowDialog(isShowDialog);
        myReqInfo.setTag(tag);
        myReqInfo.setDownload(isDownload);
        myReqInfo.setHeadersMap(getHeaders(tag));
        myReqInfo.setParamsMap(encryptForm(tag, paramsMap));
        return http(myReqInfo, myRespHandler, myInterceptor);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    private static MyReqInfo postJson(String url, Map<String, Object> map, MyRespHandler myRespHandler, String tag, boolean isShowDialog, MyInterceptor myInterceptor, boolean isDownload) {
        MyReqInfo myReqInfo = new MyReqInfo();
        myReqInfo.setMyReqType(MyReqType.POST);
        myReqInfo.setUrl(url);
        myReqInfo.setShowDialog(isShowDialog);
        myReqInfo.setTag(tag);
        myReqInfo.setDownload(isDownload);
        myReqInfo.setHeadersMap(getHeaders(tag));
        myReqInfo.setPostString(encrypeJson(tag, map));
        myReqInfo.setPostStringContentType("application/json");
        return http(myReqInfo, myRespHandler, myInterceptor);
    }

    public static void postJson(String url, Map<String, Object> map, MyRespHandler myRespHandler) {
        postJson(url, map, myRespHandler, "", true, null, false);
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
    private static Map<String, Object> encryptForm(String tag, Map<String, Object> paramsMap) {
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

    private static String encrypeJson(String tag, Map<String, Object> paramsMap) {
//        try {
//            if (paramsMap == null) {
//                return "";
//            }
//            JSONObject newJson = new JSONObject(json);
//            newJson.put("terminal", "android");
//            newJson.put("version", UtilSystem.getVersionName(App.getApplication()));
//            newJson.put("token", AppSp.getUserToken());
//            postJson(url, newJson.toString(), respHandler, "", isShowDialog, null, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "";
    }
}


