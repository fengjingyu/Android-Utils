package com.jingyu.utils.http;

import com.jingyu.utils.function.Constants;
import com.jingyu.utils.application.PlusBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @email fengjingyu@foxmail.com
 * @description http返回的信息
 */
public class RespInfo extends PlusBean {

    /**
     * 返回的成功失败的类型，目前有5个类型
     */
    private RespType respType;

    private int httpCode;

    private Map<String, List<String>> respHeaders;

    private byte[] dataBytes;

    private String dataString;

    /**
     * throwable null为success，非空为fail
     */
    private Throwable throwable;

    public RespInfo(RespType respType, int httpCode, Map<String, List<String>> respHeaders, byte[] dataBytes, String dataString, Throwable throwable) {
        this.respType = respType;
        this.httpCode = httpCode;
        this.respHeaders = respHeaders;
        this.dataBytes = dataBytes;
        this.dataString = dataString;
        this.throwable = throwable;
    }

    public RespInfo() {
    }

    public RespType getRespType() {
        return respType;
    }

    public void setRespType(RespType respType) {
        this.respType = respType;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, List<String>> getRespHeaders() {
        if (respHeaders == null) {
            respHeaders = new HashMap<>();
        }
        return respHeaders;
    }

    public void setRespHeaders(Map<String, List<String>> respHeaders) {
        if (respHeaders == null) {
            respHeaders = new HashMap<>();
        }
        this.respHeaders = respHeaders;
    }

    public byte[] getDataBytes() {
        if (dataBytes == null) {
            dataBytes = new byte[1];
        }
        return dataBytes;
    }

    public void setDataBytes(byte[] dataBytes) {
        if (dataBytes == null) {
            dataBytes = new byte[1];
        }
        this.dataBytes = dataBytes;
    }

    public String getDataString() {
        if (dataString == null) {
            dataString = "";
        }
        return dataString;
    }

    public void setDataString(String dataString) {
        if (dataString == null) {
            dataString = "";
        }
        this.dataString = dataString;
    }

    public void setDataString(byte[] bytes) {
        try {
            dataString = new String(bytes, Constants.ENCODING_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
            dataString = "";
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isSuccessAll() {
        return RespType.SUCCESS_ALL == respType;
    }
}
