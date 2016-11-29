package com.xiaocoder.utils.http.IHttp;

import com.xiaocoder.utils.http.ReqInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface IHttpClient {

    void http(ReqInfo reqInfo, IRespHandler resphandler);

}
