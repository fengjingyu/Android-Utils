package com.jingyu.utils.http;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.RespHandler;

import java.io.InputStream;

/**
 * Created by jingyu on 2017/3/30.
 * 适配接口
 */
public class RespHandlerAdapter<T> implements RespHandler<T> {

    @Override
    public void onReadySendRequest(ReqInfo reqInfo) {
        Logger.d(TAG_RESP_HANDLER, this + "--onReadySendRequest()");
    }

    @Override
    public boolean isDownload() {
        return false;
    }

    @Override
    public void onSuccessForDownload(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream) {
        Logger.d(TAG_RESP_HANDLER, this + "--onSuccessForDownload()");
    }

    @Override
    public T onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(TAG_RESP_HANDLER, this + "-----onParse2Model()");
        return null;
    }

    @Override
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.d(TAG_RESP_HANDLER, this + "---onMatchAppStatusCode()");
        return false;
    }

    @Override
    public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(TAG_RESP_HANDLER, this + "--onSuccessButParseWrong()");
    }

    @Override
    public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.d(TAG_RESP_HANDLER, this + "--onSuccessButCodeWrong()");
    }

    @Override
    public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.d(TAG_RESP_HANDLER, this + "--onSuccessAll()");
    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(TAG_RESP_HANDLER, this + "--onFailure()");
    }

    @Override
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(TAG_RESP_HANDLER, this + "--onEnd()");
    }

    @Override
    public void onProgressing(ReqInfo reqInfo, long bytesWritten, long totalSize, double percent) {
        Logger.d(TAG_RESP_HANDLER, this + "--onProgressing()");
    }
}
