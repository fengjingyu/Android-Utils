package com.example.middle.http.json;

import android.app.Activity;

import com.example.middle.http.BaseRespHandler;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.LogHelper;
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

    /**
     * 该方法是在子线程中的，解析失败返回null
     *
     * @param xcReqInfo
     * @param xcRespInfo
     * @return
     */
    @Override
    public JsonModel onParse2Model(ReqInfo xcReqInfo, RespInfo xcRespInfo) {

        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return JsonParse.getJsonParseData(xcRespInfo.getDataString(), JsonModel.class);
    }
}
