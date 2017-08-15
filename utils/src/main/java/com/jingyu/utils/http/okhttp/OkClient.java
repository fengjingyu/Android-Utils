package com.jingyu.utils.http.okhttp;

import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.util.UtilCollections;
import com.jingyu.utils.util.UtilString;
import com.jingyu.utils.http.UploadFileWrapper;
import com.jingyu.utils.http.IHttp.HttpClient;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.IHttp.RespHandler;

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
public class OkClient implements HttpClient {

    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    public void http(ReqInfo reqInfo) {
        http(reqInfo, null, null);
    }

    @Override
    public void http(ReqInfo reqInfo, RespHandler respHandler) {
        http(reqInfo, respHandler, null);
    }

    @Override
    public void http(ReqInfo reqInfo, RespHandler respHandler, Interceptor interceptor) {
        try {
            // 是否拦截请求
            if (interceptor != null && interceptor.interceptReqSend(reqInfo)) {
                return;
            }

            if (respHandler != null) {
                respHandler.onReadySendRequest(reqInfo);
            }

            // 创建请求
            Request request = createRequest(reqInfo);

            httpClient.newCall(request).enqueue(new OkCallback(reqInfo, respHandler, interceptor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Request createRequest(ReqInfo reqInfo) {
        Request.Builder requestBuilder = new Request.Builder();
        // 构建请求方式、参数、url
        buildeTypeParamsUrl(reqInfo, requestBuilder);
        // 构建请求header
        buildHeader(reqInfo, requestBuilder);
        // 返回request
        return requestBuilder.build();
    }

    private void buildeTypeParamsUrl(ReqInfo reqInfo, Request.Builder requestBuilder) {

        if (isGet(reqInfo, requestBuilder)) {
            return;
        }

        if (isPost(reqInfo, requestBuilder)) {
            return;
        }

        throw new RuntimeException("buildeTypeParamsUrl()---请求类型不匹配");

    }

    private boolean isPost(ReqInfo reqInfo, Request.Builder requestBuilder) {
        if (reqInfo.isPost()) {

            requestBuilder.url(reqInfo.getUrl());

            if (isPostString(reqInfo, requestBuilder)) {
                return true;
            }

            if (isPostForm(reqInfo, requestBuilder)) {
                return true;
            }

            if (isPostMultiForm(reqInfo, requestBuilder)) {
                return true;
            }

        }
        return false;
    }

    public boolean isPostString(ReqInfo reqInfo, Request.Builder requestBuilder) {
        if (UtilString.isAvaliable(reqInfo.getPostStringContentType())) {
            // requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json));
            requestBuilder.post(RequestBody.create(MediaType.parse(reqInfo.getPostStringContentType()), reqInfo.getPostString()));
            return true;
        }
        return false;
    }

    private boolean isPostForm(ReqInfo reqInfo, Request.Builder requestBuilder) {
        if (isIncludeFile(reqInfo)) {
            return false;
        }

        Map<String, Object> paramsMap = reqInfo.getParamsMap();

        if (UtilCollections.isMapAvaliable(paramsMap)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            requestBuilder.post(formBodyBuilder.build());
        }

        return true;
    }

    private boolean isPostMultiForm(ReqInfo reqInfo, Request.Builder requestBuilder) {

        Map<String, Object> paramsMap = reqInfo.getParamsMap();

        if (UtilCollections.isMapAvaliable(paramsMap)) {

            MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
            multiBuilder.setType(MultipartBody.FORM);

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {

                if (entry.getValue() == null) {
                    continue;
                }

                if (entry.getValue() instanceof File) {

                    UploadFileWrapper uploadFileWrapper = new UploadFileWrapper((File) entry.getValue());

                    if (uploadFileWrapper.isExist()) {
                        multiBuilder.addFormDataPart(entry.getKey(), uploadFileWrapper.getName(), RequestBody.create(uploadFileWrapper.getMediaType(), uploadFileWrapper.getFile()));
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

    private boolean isIncludeFile(ReqInfo reqInfo) {

        Map<String, Object> paramsMap = reqInfo.getParamsMap();

        if (UtilCollections.isMapAvaliable(paramsMap)) {
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (entry.getValue() instanceof File) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGet(ReqInfo reqInfo, Request.Builder builder) {
        if (reqInfo.isGet()) {

            builder.get().url(reqInfo.getUrl() + reqInfo.buildGetParams(reqInfo.getParamsMap()));

            return true;
        }
        return false;
    }


    private Request.Builder buildHeader(ReqInfo reqInfo, Request.Builder builder) {
        Map<String, List<String>> headers = reqInfo.getHeadersMap();

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
