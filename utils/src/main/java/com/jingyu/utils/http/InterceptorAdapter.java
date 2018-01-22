package com.jingyu.utils.http;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.Interceptor;
import com.jingyu.utils.http.okhttp.OkCallback;

/**
 * Created by jingyu on 2017/3/30.
 * 适配接口
 */
public abstract class InterceptorAdapter implements Interceptor {
    @Override
    public boolean interceptReqSend(ReqInfo reqInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "interceptReqSend()");
        return false;
    }

    @Override
    public void interceptRespEnd(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "interceptRespEnd()");
    }
}
