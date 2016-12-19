package com.jingyu.utils.http.IHttp;

import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface Interceptor {
    /**
     * 在RespHandler的onReadySendRequest()之前
     *
     * @return false 为继续发送请求，true 为拦截该次请求的发送
     */
    boolean interceptReqSend(ReqInfo reqInfo);

    /**
     * 响应刚刚返回的时候,这里 可以添加或修改或拦截响应的内容
     *
     * @return false RespHandler继续回调, true 为RespHandler不回调了
     */
    boolean interceptRespCome(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * 可以控制串行的请求
     * <p/>
     * 一个请求完全结束后，即调用完RespHandler的end()之后
     */
    void interceptRespDone(ReqInfo reqInfo, RespInfo respInfo);

}
