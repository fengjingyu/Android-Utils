package com.jingyu.middle.http.gson;

import android.app.Activity;

import com.jingyu.middle.http.BaseRespHandler;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;


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
    public T onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {

        Logger.d(TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return GsonParse.fromJson(respInfo.getDataString(), clazz);
    }
}
