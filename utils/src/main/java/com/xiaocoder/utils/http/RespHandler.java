package com.xiaocoder.utils.http;

import com.xiaocoder.utils.application.Constants;
import com.xiaocoder.utils.http.IHttp.IRespHandler;
import com.xiaocoder.utils.io.LogHelper;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 回调接口的一个抽象类（适配接口）
 */
public abstract class RespHandler<T> implements IRespHandler<T> {

    @Override
    public void onReadySendRequest(ReqInfo reqInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onReadySendRequest()");
    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onFailure()");
    }

    @Override
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onMatchAppStatusCode()");
        return false;
    }

    @Override
    public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccessAll()");
    }

    @Override
    public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccessButParseWrong()");
    }

    @Override
    public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccessButCodeWrong()");
    }

    @Override
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "----onEnd()");
    }

    @Override
    public void onProgressing(ReqInfo reqInfo, long bytesWritten, long totalSize, double percent) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "----onProgressing()" + (percent * 100) + "%");
    }

}

