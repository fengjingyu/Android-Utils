package com.jingyu.android.middle.config.okhttp.resp;

import android.app.Activity;

import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.utils.json.JsonBean;
import com.jingyu.utils.json.JsonParse;

/**
 * @author fengjingyu@foxmail.com
 * @description 适用于小接口（如添加收藏等接口）
 */
public class JsonRespHandler extends BaseRespHandler<JsonRespHandler.MyJsonBean> {

    public JsonRespHandler(Activity activity) {
        super(activity);
    }

    public JsonRespHandler() {
    }

    /**
     * 该方法是在子线程中的，解析失败返回null
     */
    @Override
    public MyJsonBean onParse2Model(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        super.onParse2Model(myReqInfo, myRespInfo);
        return JsonParse.getJsonParseData(myRespInfo.getDataString(), MyJsonBean.class);
    }

    public class MyJsonBean extends JsonBean implements IRespMsgCode {

        //todo
        @Override
        public String getCode() {
            return getString("code");
        }

        //todo
        @Override
        public String getMsg() {
            return getString("msg");
        }
    }
}
