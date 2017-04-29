package com.jingyu.utils.http;

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
public class ReqInfo extends PlusBean {
    /**
     * POST  GET
     */
    private ReqType reqType = ReqType.GET;
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

    public ReqType getReqType() {
        return reqType;
    }

    public void setReqType(ReqType reqType) {
        this.reqType = reqType;
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

    @Override
    public String toString() {
        return "ReqInfo{" +
                "reqType=" + reqType +
                ", url='" + url + '\'' +
                ", headersMap=" + headersMap +
                ", paramsMap=" + paramsMap +
                ", isShowDialog=" + isShowDialog +
                ", postStringContentType='" + postStringContentType + '\'' +
                ", postString='" + postString + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public ReqInfo() {
    }

    public ReqInfo(String url) {
        this.url = url;
    }

    public ReqInfo(String url, Map<String, Object> paramsMap) {
        this.url = url;
        this.paramsMap = paramsMap;
    }

    public ReqInfo(ReqType reqType, String url, Map<String, Object> paramsMap) {
        this.reqType = reqType;
        this.url = url;
        this.paramsMap = paramsMap;
    }

    public ReqInfo(ReqType reqType, String url, Map<String, List<String>> headersMap, Map<String, Object> paramsMap) {
        this.reqType = reqType;
        this.url = url;
        this.headersMap = headersMap;
        this.paramsMap = paramsMap;
    }

    public ReqInfo(ReqType reqType, String url, Map<String, List<String>> headersMap, Map<String, Object> paramsMap, String tag) {
        this.reqType = reqType;
        this.url = url;
        this.headersMap = headersMap;
        this.paramsMap = paramsMap;
        this.tag = tag;
    }

    public boolean isGet() {
        return getReqType() == ReqType.GET;
    }

    public boolean isPost() {
        return getReqType() == ReqType.POST;
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
            // 有参数,就一定会多一个AND
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