package com.xiaocoder.utils.http.IHttp;

import com.xiaocoder.utils.http.ReqInfo;

/**
 * @author xiaocoder 2014-10-17 下午1:52:54
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface IHttpClient {

    void http(ReqInfo reqInfo, IRespHandler resphandler);

}
