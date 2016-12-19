package com.jingyu.test_middle.http.gson;

import android.app.Activity;

import com.jingyu.test_middle.http.BaseRespHandler;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description gson解析的实现类
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

    @Override
    public T onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {

        Logger.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return SXGsonParse.fromJson(respInfo.getDataString(), clazz);
    }
}
