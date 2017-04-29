package com.jingyu.utils.http.IHttp;

import com.jingyu.utils.http.ReqInfo;

/**
 * @author fengjingyu@foxmail.com
 */
public interface HttpClient {

    void http(ReqInfo reqInfo, RespHandler resphandler, Interceptor interceptor);

    void http(ReqInfo reqInfo, RespHandler resphandler);

    void http(ReqInfo reqInfo);

    //todo
    //void https(ReqInfo reqInfo, RespHandler resphandler, Interceptor interceptor);

}
