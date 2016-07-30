package com.example.middle.http.gson;

import android.app.Activity;

import com.example.middle.http.BaseRespHandler;
import com.xiaocoder.utils.application.XCConstant;
import com.xiaocoder.utils.http.XCReqInfo;
import com.xiaocoder.utils.http.XCRespInfo;
import com.xiaocoder.utils.io.XCLog;
import com.xiaocoder.utils.json.SXGsonParse;

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

    /**
     * 该方法是在子线程中的，解析失败返回null
     *
     * @param xcReqInfo
     * @param xcRespInfo
     * @return
     */
    @Override
    public T onParse2Model(XCReqInfo xcReqInfo, XCRespInfo xcRespInfo) {

        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return SXGsonParse.fromJson(xcRespInfo.getDataString(), clazz);
    }
}
