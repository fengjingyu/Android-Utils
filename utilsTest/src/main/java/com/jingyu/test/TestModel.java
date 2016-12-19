package com.jingyu.test;

import com.jingyu.utils.application.Bean;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TestModel extends Bean {

    private String msg;

    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
