package com.xiaocoder.test_middle.http.json;

import android.app.Activity;

import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.function.helper.LogHelper;
import com.xiaocoder.utils.json.JsonParse;
import com.xiaocoder.test_middle.http.BaseRespHandler;

/**
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
    public JsonModel onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {

        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return JsonParse.getJsonParseData(respInfo.getDataString(), JsonModel.class);
    }
}
