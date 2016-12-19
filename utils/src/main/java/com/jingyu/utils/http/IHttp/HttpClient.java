package com.jingyu.utils.http.IHttp;

import com.jingyu.utils.http.ReqInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface HttpClient {

    void http(ReqInfo reqInfo, RespHandler resphandler, Interceptor interceptor);

}
