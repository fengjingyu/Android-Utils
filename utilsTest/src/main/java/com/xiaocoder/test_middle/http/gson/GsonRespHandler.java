package com.xiaocoder.test_middle.http.gson;

import android.app.Activity;

import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.function.helper.LogHelper;
import com.xiaocoder.test_middle.http.BaseRespHandler;

/**
 * @author xiaocoder
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

        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return SXGsonParse.fromJson(respInfo.getDataString(), clazz);
    }
}
