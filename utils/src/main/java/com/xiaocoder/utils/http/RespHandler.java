package com.xiaocoder.utils.http;

import com.xiaocoder.utils.application.Constants;
import com.xiaocoder.utils.io.LogHelper;

/**
 * @email fengjingyu@foxmail.com
 * @description 回调接口的一个抽象类（适配接口）
 */
public abstract class RespHandler<T> {

    /**
     * 发送请求之前
     * <p/>
     * 可以showdialog,加密等
     */
    public void onReadySendRequest(ReqInfo reqInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onReadySendRequest()");
    }

    /**
     * 如果解析失败：一定得返回null
     * 最好放在子线程
     */
    abstract T onParse2Model(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * 对返回状态码的一个判断，每个项目的认定操作成功的状态码或结构可能不同，在这里统一判断
     * <p/>
     * 如果调用到这个方法，表示解析一定是成功的；如果解析失败，是不会调用这个方法的
     */
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onMatchAppStatusCode()");
        return false;
    }

    /**
     * http 请求成功，解析失败
     */
    public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccessButParseWrong()");
    }

    /**
     * http 请求成功，解析成功，项目业务逻辑的状态码有误
     */
    public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccessButCodeWrong()");
    }

    /**
     * http请求成功，解析成功，状态码成功，回调该方法
     */
    public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onSuccessAll()");
    }

    /**
     * http请求失败，回调该方法
     */
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "-----onFailure()");
    }

    /**
     * onSuccess%或 onFailure 的逻辑调用完后回调该方法
     * <p/>
     * 可以关闭dialog
     */
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "----onEnd()");
    }

    /**
     * 加载进度
     */
    public void onProgressing(ReqInfo reqInfo, long bytesWritten, long totalSize, double percent) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "----onProgressing()" + (percent * 100) + "%");
    }

}

