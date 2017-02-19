package com.jingyu.middle.http.json;

import android.app.Activity;

import com.jingyu.middle.http.BaseRespHandler;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.json.JsonParse;

import static com.jingyu.utils.function.Logger.TAG_RESP_HANDLER;

/**
 * @author fengjingyu@foxmail.com
 * @description jsonparse解析的http回调类
 */
public class JsonRespHandler extends BaseRespHandler<JsonModel> {

    public JsonRespHandler(Activity activity) {
        super(activity);
    }

    public JsonRespHandler() {
    }

    /**
     * 该方法是在子线程中的，解析失败返回null
     */
    @Override
    public JsonModel onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {

        Logger.i(TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return JsonParse.getJsonParseData(respInfo.getDataString(), JsonModel.class);
    }
}
