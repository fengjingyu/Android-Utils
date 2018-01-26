package com.jingyu.android.init.middle.config.okhttp.req;

import com.jingyu.utils.application.PlusBean;
import com.jingyu.utils.util.UtilCollections;
import com.jingyu.utils.util.UtilString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 *  http请求的信息
 */
public class MyReqInfo extends PlusBean {
    /**
     * POST  GET
     */
    private MyReqType myReqType = MyReqType.GET;
    /**
     * 接口地址
     */
    private String url = "";
    /**
     * http的请求头
     */
    private Map<String, List<String>> headersMap;
    /**
     * http的原始请求参数，未加密
     */
    private Map<String, Object> paramsMap;
    /**
     * 是否显示加载dialog
     */
    private boolean isShowDialog;
    /**
     * postString的ContentType
     */
    private String postStringContentType = "";
    /**
     * postString的内容
     */
    private String postString = "";
    /**
     * 标识
     */
    private String tag;
    /**
     * 是否是下载
     */
    private boolean isDownload;

    public MyReqType getMyReqType() {
        return myReqType;
    }

    public void setMyReqType(MyReqType myReqType) {
        this.myReqType = myReqType;
    }

    public String getUrl() {
        if (url == null || url.trim().length() == 0) {
            url = "";
        }
        return url;
    }

    public void setUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            url = "";
        }
        this.url = url;
    }

    public Map<String, List<String>> getHeadersMap() {
        if (headersMap == null) {
            headersMap = new HashMap<>();
        }
        return headersMap;
    }

    public void setHeadersMap(Map<String, List<String>> headersMap) {
        if (headersMap == null) {
            headersMap = new HashMap<>();
        }
        this.headersMap = headersMap;
    }

    public Map<String, Object> getParamsMap() {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        this.paramsMap = paramsMap;
    }

    public boolean isShowDialog() {
        return isShowDialog;
    }

    public void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    @Override
    public String toString() {
        return "MyReqInfo{" +
                "myReqType=" + myReqType +
                ", url='" + url + '\'' +
                ", headersMap=" + headersMap +
                ", paramsMap=" + paramsMap +
                ", isShowDialog=" + isShowDialog +
                ", postStringContentType='" + postStringContentType + '\'' +
                ", postString='" + postString + '\'' +
                ", tag='" + tag + '\'' +
                ", isDownload=" + isDownload +
                '}';
    }

    public MyReqInfo() {
    }

    public MyReqInfo(String url) {
        this.url = url;
    }

    public MyReqInfo(String url, boolean isDownload) {
        this.url = url;
        this.isDownload = isDownload;
    }

    public MyReqInfo(String url, Map<String, Object> paramsMap) {
        this.url = url;
        this.paramsMap = paramsMap;
    }

    public MyReqInfo(MyReqType myReqType, String url, Map<String, Object> paramsMap) {
        this.myReqType = myReqType;
        this.url = url;
        this.paramsMap = paramsMap;
    }

    public MyReqInfo(MyReqType myReqType, String url, Map<String, List<String>> headersMap, Map<String, Object> paramsMap) {
        this.myReqType = myReqType;
        this.url = url;
        this.headersMap = headersMap;
        this.paramsMap = paramsMap;
    }

    public MyReqInfo(MyReqType myReqType, String url, Map<String, List<String>> headersMap, Map<String, Object> paramsMap, String tag) {
        this.myReqType = myReqType;
        this.url = url;
        this.headersMap = headersMap;
        this.paramsMap = paramsMap;
        this.tag = tag;
    }

    public MyReqInfo(MyReqType myReqType, String url, Map<String, List<String>> headersMap, Map<String, Object> paramsMap, boolean isShowDialog, String postStringContentType, String postString, String tag, boolean isDownload) {
        this.myReqType = myReqType;
        this.url = url;
        this.headersMap = headersMap;
        this.paramsMap = paramsMap;
        this.isShowDialog = isShowDialog;
        this.postStringContentType = postStringContentType;
        this.postString = postString;
        this.tag = tag;
        this.isDownload = isDownload;
    }

    public boolean isGet() {
        return getMyReqType() == MyReqType.GET;
    }

    public boolean isPost() {
        return getMyReqType() == MyReqType.POST;
    }

    public static final String AND = "&";

    public static final String EQUAL = "=";

    public static final String QUESTION = "?";

    /**
     * 构建get方式的参数串（未拼接ur）
     *
     * @return ?abc=123
     */
    public String buildGetParams(Map<String, Object> params) {

        if (UtilCollections.isMapAvaliable(params)) {
            StringBuilder sb = new StringBuilder();

            sb.append(QUESTION);

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                sb.append(entry.getKey() + EQUAL + entry.getValue() + AND);
            }

            return UtilString.subStringBeforeLastSimbol(sb.toString(), AND);

        } else {
            // 无参数
            return "";
        }
    }

    public String getPostStringContentType() {
        return postStringContentType;
    }

    public void setPostStringContentType(String postStringContentType) {
        this.postStringContentType = postStringContentType;
    }

    public String getPostString() {
        return postString;
    }

    public void setPostString(String postString) {
        this.postString = postString;
    }

}