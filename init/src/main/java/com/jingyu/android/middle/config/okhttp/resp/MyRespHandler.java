package com.jingyu.android.middle.config.okhttp.resp;

import com.jingyu.android.middle.config.okhttp.MyCallback;
import com.jingyu.android.middle.config.okhttp.req.MyReqInfo;
import com.jingyu.utils.function.Logger;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 *         http回调
 */
public abstract class MyRespHandler<T> {
    /**
     * 发送请求之前
     * <p>
     * 可以showdialog等
     */
    public void onReadySendRequest(MyReqInfo myReqInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onReadySendRequest()");
    }

    /**
     * 下载的回调,如果isDownload是true,则回调 onSuccessForDownload/onFailure , onEnd
     * 子线程
     */
    public void onSuccessForDownload(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onSuccessForDownload()");
    }

    /**
     * 如果解析失败：一定得返回null,回调onSuccessButParseWrong()
     * 如果解析成功: 继续回调onMatchAppStatusCode()
     * 子线程
     */
    public T onParse2Model(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "-----onParse2Model()");
        return null;
    }

    /**
     * 对返回状态码的一个判断，每个项目的认定操作成功的状态码或结构可能不同，在这里统一判断
     * <p>
     * 如果调用到这个方法，表示解析一定是成功的；如果解析失败，是不会调用这个方法的
     * false:回调onSuccessButCodeWrong()
     * true: 回调onSuccessAll()
     */
    public boolean onMatchAppStatusCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        Logger.d(MyCallback.TAG_HTTP, this + "---onMatchAppStatusCode()");
        return false;
    }

    /**
     * http 请求成功，解析失败
     */
    public void onSuccessButParseWrong(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onSuccessButParseWrong()");
    }

    /**
     * http 请求成功，解析成功，项目业务逻辑的状态码有误
     */
    public void onSuccessButCodeWrong(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onSuccessButCodeWrong()");
    }

    /**
     * http请求成功，解析成功，状态码成功，回调该方法
     */
    public void onSuccessAll(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onSuccessAll()");
    }

    /**
     * http请求失败，回调该方法
     */
    public void onFailure(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onFailure()");
    }

    /**
     * onSuccess  或 onFailure 的逻辑调用完后回调该方法
     * <p>
     * 可以关闭dialog
     */
    public void onEnd(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onEnd()");
    }

    /**
     * 加载进度
     */
    public void onProgressing(MyReqInfo myReqInfo, long bytesWritten, long totalSize, double percent) {
        Logger.d(MyCallback.TAG_HTTP, this + "--onProgressing()");
    }
}
