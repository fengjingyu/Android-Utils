package com.xiaocoder.utils.http.IHttp;

import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface Interceptor {

    /**
     * @return false 为继续发送请求，true 为拦截该次请求
     */
    boolean isInterceptRequest(ReqInfo reqInfo);

    /**
     * 响应刚刚返回的时候
     */
    void onRespCome(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * 一个请求完全结束后，即调用完RespHandler的end()后,调用该方法
     */
    void onRespDone(ReqInfo reqInfo, RespInfo respInfo);

}
