package com.xiaocoder.test_middle.http.json;

import android.app.Activity;

import com.xiaocoder.utils.application.XCConstant;
import com.xiaocoder.utils.http.XCReqInfo;
import com.xiaocoder.utils.http.XCRespInfo;
import com.xiaocoder.utils.io.XCLog;
import com.xiaocoder.utils.json.XCJsonParse;
import com.xiaocoder.test_middle.http.BaseRespHandler;

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

    @Override
    public JsonModel onParse2Model(XCReqInfo xcReqInfo, XCRespInfo xcRespInfo) {

        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return XCJsonParse.getJsonParseData(xcRespInfo.getDataString(), JsonModel.class);
    }
}
