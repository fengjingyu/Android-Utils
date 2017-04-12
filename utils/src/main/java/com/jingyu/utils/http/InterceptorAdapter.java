package com.jingyu.utils.http;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.Interceptor;

/**
 * Created by jingyu on 2017/3/30.
 * 适配接口
 */
public abstract class InterceptorAdapter implements Interceptor {
    @Override
    public boolean interceptReqSend(ReqInfo reqInfo) {
        Logger.d(this + "interceptReqSend()");
        return false;
    }

    @Override
    public void interceptRespEnd(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(this + "interceptRespEnd()");
    }
}
