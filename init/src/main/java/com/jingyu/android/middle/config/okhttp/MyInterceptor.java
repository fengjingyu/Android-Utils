package com.jingyu.android.middle.config.okhttp;

import com.jingyu.android.common.log.Logger;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.android.middle.config.okhttp.resp.MyRespInfo;

/**
 * @author fengjingyu@foxmail.com
 *         该类的方法都是在主线程回调
 */
public abstract class MyInterceptor{
    /**
     * 在回调RespHandler的onReadySendRequest()之前调用该方法
     *
     * @return false 为继续发送请求，true 为拦截该次请求的发送
     */
    public boolean interceptReqSend(MyReqInfo myReqInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "interceptReqSend()");
        return false;
    }

    /**
     * 一个请求完全结束后，即调用完RespHandler的end()之后,可以监听串行的请求
     */
    public void interceptRespEnd(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "interceptRespEnd()");
    }
}