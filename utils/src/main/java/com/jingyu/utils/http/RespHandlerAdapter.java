package com.jingyu.utils.http;

import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.IHttp.RespHandler;
import com.jingyu.utils.http.okhttp.OkCallback;

import java.io.InputStream;

/**
 * Created by jingyu on 2017/3/30.
 * 适配接口
 */
public class RespHandlerAdapter<T> implements RespHandler<T> {

    @Override
    public void onReadySendRequest(ReqInfo reqInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onReadySendRequest()");
    }

    @Override
    public void onSuccessForDownload(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onSuccessForDownload()");
    }

    @Override
    public T onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "-----onParse2Model()");
        return null;
    }

    @Override
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.d(OkCallback.TAG_HTTP, this + "---onMatchAppStatusCode()");
        return false;
    }

    @Override
    public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onSuccessButParseWrong()");
    }

    @Override
    public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onSuccessButCodeWrong()");
    }

    @Override
    public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onSuccessAll()");
    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onFailure()");
    }

    @Override
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onEnd()");
    }

    @Override
    public void onProgressing(ReqInfo reqInfo, long bytesWritten, long totalSize, double percent) {
        Logger.d(OkCallback.TAG_HTTP, this + "--onProgressing()");
    }
}
