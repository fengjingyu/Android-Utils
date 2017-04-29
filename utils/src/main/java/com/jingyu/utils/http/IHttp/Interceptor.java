package com.jingyu.utils.http.IHttp;

import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

/**
 * @author fengjingyu@foxmail.com
 *  该类的方法都是在主线程回调
 */
public interface Interceptor {
    /**
     * 在回调RespHandler的onReadySendRequest()之前调用该方法
     *
     * @return false 为继续发送请求，true 为拦截该次请求的发送
     */
    boolean interceptReqSend(ReqInfo reqInfo);

    /**
     * 一个请求完全结束后，即调用完RespHandler的end()之后,可以监听串行的请求
     */
    void interceptRespEnd(ReqInfo reqInfo, RespInfo respInfo);

}
