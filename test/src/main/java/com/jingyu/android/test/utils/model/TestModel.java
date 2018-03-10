package com.jingyu.android.test.utils.model;

import com.jingyu.android.middle.config.okhttp.resp.IRespMsgCode;
import com.jingyu.java.mytool.basic.bean.CloneBean;
/**
 * @author fengjingyu@foxmail.com
 */
public class TestModel extends CloneBean implements IRespMsgCode {

    private String msg;

    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return super.toString() + "-->TestModel{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
