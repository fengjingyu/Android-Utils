package com.xiaocoder.test_middle.http.json;

import com.xiaocoder.utils.json.JsonBean;
import com.xiaocoder.test_middle.http.IHttpRespInfo;

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
