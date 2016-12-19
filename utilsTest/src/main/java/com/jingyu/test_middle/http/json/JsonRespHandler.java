package com.jingyu.test_middle.http.json;

import android.app.Activity;

import com.jingyu.test_middle.http.BaseRespHandler;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.json.JsonParse;

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

        Logger.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return JsonParse.getJsonParseData(respInfo.getDataString(), JsonModel.class);
    }
}
