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


    AIDLBean getBeanIn(in AIDLBean bean);

    // out传入的参数,service可以接收到bena,但是bean里的值全部都没有,都的是默认值
    AIDLBean getBeanOut(out AIDLBean bean);

    AIDLBean getBeanInOut(inout AIDLBean bean);

    void getBeanIn2(in AIDLBean bean);

    void getBeanOut2(out AIDLBean bean);

    void getBeanInOut2(inout AIDLBean bean);

    String registerCallBack(in AIDLCallBack callBack);

}
