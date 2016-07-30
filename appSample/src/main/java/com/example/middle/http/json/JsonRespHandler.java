package com.example.middle.http.json;

import android.app.Activity;

import com.example.middle.http.BaseRespHandler;
import com.xiaocoder.utils.application.XCConstant;
import com.xiaocoder.utils.http.XCReqInfo;
import com.xiaocoder.utils.http.XCRespInfo;
import com.xiaocoder.utils.io.XCLog;
import com.xiaocoder.utils.json.XCJsonParse;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description JsonModel jsonparse解析实现的handler
 */
public class JsonRespHandler extends BaseRespHandler<JsonModel> {

    public JsonRespHandler(Activity activity) {
        super(activity);
    }

    public JsonRespHandler() {

    }

    /**
     * 该方法是在子线程中的，解析失败返回null
     *
     * @param xcReqInfo
     * @param xcRespInfo
     * @return
     */
    @Override
    public JsonModel onParse2Model(XCReqInfo xcReqInfo, XCRespInfo xcRespInfo) {

        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return XCJsonParse.getJsonParseData(xcRespInfo.getDataString(), JsonModel.class);
    }
}
