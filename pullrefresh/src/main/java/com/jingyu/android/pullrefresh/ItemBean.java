package com.jingyu.android.pullrefresh;

import com.jingyu.java.mytool.bean.CloneBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xtyx_jy on 2017/4/26.
 */
public class ItemBean extends CloneBean {

    private String code;
    private String message;
    private List<DataBean> data = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        String id = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public DataBean(String id) {
            this.id = id;
        }
    }
}
