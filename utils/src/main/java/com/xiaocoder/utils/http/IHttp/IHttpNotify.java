package com.xiaocoder.utils.http.IHttp;

import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description 对http请求发出之前和http回调结束之后的监听
 * 可以拦截请求是否发送 与 控制串行的的请求
 */
public interface IHttpNotify {

    /**
     * 正准备发，还没有发出去,即在respHandler的onReadySendRequest()之前调用该方法
     *
     * @return true 为继续调用后面的方法，false 为拦截后面的方法即不会调用sucess /failure end
     */
    boolean onReadySendNotify(ReqInfo reqInfo);

    /**
     * 一个请求完全结束后，即调用完end后,回调该方法
     */
    void onEndNotify(ReqInfo reqInfo, RespInfo respInfo);

}
