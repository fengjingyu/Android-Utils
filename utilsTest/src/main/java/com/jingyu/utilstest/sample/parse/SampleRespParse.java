package com.jingyu.utilstest.sample.parse;

import android.app.Activity;

import com.jingyu.middle.http.BaseRespHandler;
import com.jingyu.utilstest.sample.model.SampleHttpModel;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

/**
 * @email fengjingyu@foxmail.com
 * @description 解析类
 */
public class SampleRespParse extends BaseRespHandler<SampleHttpModel> {

    public SampleRespParse(Activity activityContext) {
        super(activityContext);
    }

    public SampleRespParse() {
    }

    @Override
    public SampleHttpModel onParse2Model(ReqInfo xcReqInfo, RespInfo xcRespInfo) {

        String respString = xcRespInfo.getDataString();
        // 解析respString，这里省略
        // 假数据
        SampleHttpModel model = new SampleHttpModel();
        model.setCode("1");
        model.setMsg("msg");
        model.setName("name");

        return model;
    }
}
