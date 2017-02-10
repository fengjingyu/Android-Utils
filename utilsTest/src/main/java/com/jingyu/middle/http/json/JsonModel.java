package com.jingyu.middle.http.json;

import com.jingyu.middle.http.IHttpRespInfo;
import com.jingyu.utils.json.JsonBean;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class JsonModel extends JsonBean implements IHttpRespInfo {

    @Override
    public String getCode() {
        return getString("code");
    }

    @Override
    public String getMsg() {
        return getString("msg");
    }
}
