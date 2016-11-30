package com.xiaocoder.utils.http;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class Interceptor {

    /**
     * @return false 为继续发送请求，true 为拦截该次请求
     */
    public boolean isInterceptRequest(ReqInfo reqInfo) {
        return false;
    }

    /**
     * 响应刚刚返回的时候
     */
    public void onRespCome(ReqInfo reqInfo, RespInfo respInfo) {
    }

    /**
     * 一个请求完全结束后，即调用完RespHandler的end()后,调用该方法
     */
    public void onRespDone(ReqInfo reqInfo, RespInfo respInfo) {
    }

}
