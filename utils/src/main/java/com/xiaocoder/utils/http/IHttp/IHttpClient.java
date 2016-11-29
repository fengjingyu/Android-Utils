package com.xiaocoder.utils.http.IHttp;

import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespHandler;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface IHttpClient {

    void http(ReqInfo reqInfo, RespHandler resphandler);

}
