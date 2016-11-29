package com.xiaocoder.utils.http.okhttp;

import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.http.HttpCtrl;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.http.RespType;
import com.xiaocoder.utils.function.helper.LogHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @email fengjingyu@foxmail.com
 * @description okhttp实现，该库的回调在子线程中的
 */
public class OkRespHandler implements Callback {

    private HttpCtrl httpCtrl;

    public OkRespHandler(HttpCtrl httpCtrl) {
        this.httpCtrl = httpCtrl;
    }

    @Override
    public void onFailure(Call call, IOException e) {

        if (httpCtrl.getRespHandler() != null) {

            RespInfo respInfo = new RespInfo();

            respInfo.setRespType(RespType.FAILURE);
            respInfo.setThrowable(e);

            httpCtrl.handlerSuccess(respInfo);

        } else {
            LogHelper.i(Constants.TAG_HTTP, "onFailure--未传入handler--" + httpCtrl.getReqInfo());
        }

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (httpCtrl.getRespHandler() != null) {

            RespInfo respInfo = new RespInfo();
            respInfo.setHttpCode(response.code());
            respInfo.setRespHeaders(headers2Map(response.headers()));

            // 只能读一次，否则异常
            byte[] bytes = response.body().bytes();
            respInfo.setDataBytes(bytes);
            respInfo.setDataString(bytes);

            respInfo.setRespType(RespType.SUCCESS_WAIT_TO_PARSE);
            respInfo.setThrowable(null);

            httpCtrl.handlerSuccess(respInfo);
        } else {
            LogHelper.i(Constants.TAG_HTTP, "onSuccess--未传入handler--" + httpCtrl.getReqInfo());
        }

    }

    private Map<String, List<String>> headers2Map(Headers headers) {
        return headers.toMultimap();
    }
}
