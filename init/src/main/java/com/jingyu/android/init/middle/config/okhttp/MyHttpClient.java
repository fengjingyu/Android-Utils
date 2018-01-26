package com.jingyu.android.init.middle.config.okhttp;

import com.jingyu.android.init.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.init.middle.config.okhttp.resp.MyRespHandler;
import com.jingyu.utils.util.UtilCollections;
import com.jingyu.utils.util.UtilString;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author fengjingyu@foxmail.com
 *         用的是okhttp库
 */
public class MyHttpClient {

    private OkHttpClient okHttpClient;

    protected OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    public MyHttpClient() {
        okHttpClient = getOkHttpClient();
    }

    public void http(MyReqInfo myReqInfo) {
        http(myReqInfo, null, null);
    }

    public void http(MyReqInfo myReqInfo, MyRespHandler myRespHandler) {
        http(myReqInfo, myRespHandler, null);
    }

    public void http(MyReqInfo myReqInfo, MyRespHandler myRespHandler, MyInterceptor myInterceptor) {
        try {
            // 是否拦截请求
            if (myInterceptor != null && myInterceptor.interceptReqSend(myReqInfo)) {
                return;
            }

            if (myRespHandler != null) {
                myRespHandler.onReadySendRequest(myReqInfo);
            }

            // 创建请求
            Request request = createRequest(myReqInfo);

            okHttpClient.newCall(request).enqueue(new MyCallback(myReqInfo, myRespHandler, myInterceptor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Request createRequest(MyReqInfo myReqInfo) {
        Request.Builder requestBuilder = new Request.Builder();
        // 构建请求方式、参数、url
        buildeTypeParamsUrl(myReqInfo, requestBuilder);
        // 构建请求header
        buildHeader(myReqInfo, requestBuilder);
        // 返回request
        return requestBuilder.build();
    }

    private void buildeTypeParamsUrl(MyReqInfo myReqInfo, Request.Builder requestBuilder) {

        if (isGet(myReqInfo, requestBuilder)) {
            return;
        }

        if (isPost(myReqInfo, requestBuilder)) {
            return;
        }

        throw new RuntimeException("buildeTypeParamsUrl()---请求类型不匹配");

    }

    private boolean isPost(MyReqInfo myReqInfo, Request.Builder requestBuilder) {
        if (myReqInfo.isPost()) {

            requestBuilder.url(myReqInfo.getUrl());

            if (isPostString(myReqInfo, requestBuilder)) {
                return true;
            }

            if (isPostForm(myReqInfo, requestBuilder)) {
                return true;
            }

            if (isPostMultiForm(myReqInfo, requestBuilder)) {
                return true;
            }

        }
        return false;
    }

    public boolean isPostString(MyReqInfo myReqInfo, Request.Builder requestBuilder) {
        if (UtilString.isAvaliable(myReqInfo.getPostStringContentType())) {
            // requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json));
            requestBuilder.post(RequestBody.create(MediaType.parse(myReqInfo.getPostStringContentType()), myReqInfo.getPostString()));
            return true;
        }
        return false;
    }

    private boolean isPostForm(MyReqInfo myReqInfo, Request.Builder requestBuilder) {
        if (isIncludeFile(myReqInfo)) {
            return false;
        }

        Map<String, Object> paramsMap = myReqInfo.getParamsMap();

        if (UtilCollections.isMapAvaliable(paramsMap)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            requestBuilder.post(formBodyBuilder.build());
        }

        return true;
    }

    private boolean isPostMultiForm(MyReqInfo myReqInfo, Request.Builder requestBuilder) {

        Map<String, Object> paramsMap = myReqInfo.getParamsMap();

        if (UtilCollections.isMapAvaliable(paramsMap)) {

            MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
            multiBuilder.setType(MultipartBody.FORM);

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {

                if (entry.getValue() == null) {
                    continue;
                }

                if (entry.getValue() instanceof File) {

                    MyUploadFile myUploadFile = new MyUploadFile((File) entry.getValue());

                    if (myUploadFile.isExist()) {
                        multiBuilder.addFormDataPart(entry.getKey(), myUploadFile.getName(), RequestBody.create(myUploadFile.getMediaType(), myUploadFile.getFile()));
                    } else {
                        continue;
                    }

                } else {
                    multiBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            requestBuilder.post(multiBuilder.build());
        }

        return true;
    }

    private boolean isIncludeFile(MyReqInfo myReqInfo) {

        Map<String, Object> paramsMap = myReqInfo.getParamsMap();

        if (UtilCollections.isMapAvaliable(paramsMap)) {
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (entry.getValue() instanceof File) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGet(MyReqInfo myReqInfo, Request.Builder builder) {
        if (myReqInfo.isGet()) {

            builder.get().url(myReqInfo.getUrl() + myReqInfo.buildGetParams(myReqInfo.getParamsMap()));

            return true;
        }
        return false;
    }


    private Request.Builder buildHeader(MyReqInfo myReqInfo, Request.Builder builder) {
        Map<String, List<String>> headers = myReqInfo.getHeadersMap();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {

            List<String> values = entry.getValue();
            String key = entry.getKey();

            if (UtilCollections.isListAvaliable(values)) {
                for (String value : values) {
                    builder.addHeader(key, value);
                }
            }
        }
        return builder;
    }
}
