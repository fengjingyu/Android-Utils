package com.jingyu.utilstest.model;

import com.demo.middle.http.IHttpRespInfo;
import com.jingyu.utils.application.PlusBean;

/**
 * @author fengjingyu@foxmail.com
 */
public class TestModel extends PlusBean implements IHttpRespInfo {

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
