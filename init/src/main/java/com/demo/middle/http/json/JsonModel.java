package com.demo.middle.http.json;

import com.demo.middle.http.IHttpRespInfo;
import com.jingyu.utils.json.JsonBean;

/**
 * @author fengjingyu@foxmail.com
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
