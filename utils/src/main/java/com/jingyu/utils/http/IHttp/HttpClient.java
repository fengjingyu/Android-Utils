package com.jingyu.utils.http.IHttp;

import com.jingyu.utils.http.ReqInfo;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public interface HttpClient {

    void http(ReqInfo reqInfo, RespHandler resphandler, Interceptor interceptor);

    //todo
    //void https(ReqInfo reqInfo, RespHandler resphandler, Interceptor interceptor);

}
