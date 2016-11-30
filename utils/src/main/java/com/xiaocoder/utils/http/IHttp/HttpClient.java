package com.xiaocoder.utils.http.IHttp;

import com.xiaocoder.utils.http.ReqInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface HttpClient {

    void http(ReqInfo reqInfo, RespHandler resphandler, Interceptor interceptor);

}
