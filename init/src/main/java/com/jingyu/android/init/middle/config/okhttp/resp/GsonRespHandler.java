package com.jingyu.android.init.middle.config.okhttp.resp;

import android.app.Activity;

import com.jingyu.android.init.middle.AppJson;
import com.jingyu.android.init.middle.config.okhttp.req.MyReqInfo;

/**
 * @author fengjingyu@foxmail.com
 * @description gson解析的http回调类
 */
public class GsonRespHandler<T> extends BaseRespHandler<T> {

    private Class<T> clazz;

    public GsonRespHandler(Activity activity, Class<T> clazz) {
        super(activity);
        this.clazz = clazz;
    }

    public GsonRespHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是在子线程中的，解析失败返回null
     */
    @Override
    public T onParse2Model(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        super.onParse2Model(myReqInfo, myRespInfo);
        return AppJson.parseJson(myRespInfo.getDataString(), clazz);
    }
}
