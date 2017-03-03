// IRemoteService.aidl
package com.jingyu.test.service;

import com.jingyu.test.service.AIDLBean;
import com.jingyu.test.service.AidlCallBack;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getInfo();

    List<String> getNames();

    // in：客户端的参数输入；
    // out：服务端的参数输入；
    // inout：这个可以叫输入输出参数，客户端可输入、服务端也可输入。客户端输入了参数到服务端后，服务端也可对该参数进行修改等，最后在客户端上得到的是服务端输出的参数。
    AIDLBean getAIDLBean(in AIDLBean bean);

    String registerCallBack(in AIDLCallBack callBack);

}
