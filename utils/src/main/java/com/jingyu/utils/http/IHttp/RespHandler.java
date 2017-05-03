package com.jingyu.utils.http.IHttp;

import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 *  http回调接口
 */
public interface RespHandler<T> {
    /**
     * 可查看http里每个方法的调用顺序
     */
    String TAG_RESP_HANDLER = RespHandler.class.getSimpleName();

    /**
     * 发送请求之前
     *
     * 可以showdialog等
     */
    void onReadySendRequest(ReqInfo reqInfo);

    /**
     * 下载的回调,如果isDownload是true,则回调 onSuccessForDownload/onFailure , onEnd
     * 子线程
     */
    void onSuccessForDownload(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream);

    /**
     * 如果解析失败：一定得返回null,回调onSuccessButParseWrong()
     * 如果解析成功: 继续回调onMatchAppStatusCode()
     * 子线程
     */
    T onParse2Model(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * 对返回状态码的一个判断，每个项目的认定操作成功的状态码或结构可能不同，在这里统一判断
     *
     * 如果调用到这个方法，表示解析一定是成功的；如果解析失败，是不会调用这个方法的
     * false:回调onSuccessButCodeWrong()
     * true: 回调onSuccessAll()
     */
    boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean);

    /**
     * http 请求成功，解析失败
     */
    void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * http 请求成功，解析成功，项目业务逻辑的状态码有误
     */
    void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean);

    /**
     * http请求成功，解析成功，状态码成功，回调该方法
     */
    void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean);

    /**
     * http请求失败，回调该方法
     */
    void onFailure(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * onSuccess  或 onFailure 的逻辑调用完后回调该方法
     *
     * 可以关闭dialog
     */
    void onEnd(ReqInfo reqInfo, RespInfo respInfo);

    /**
     * 加载进度
     */
    void onProgressing(ReqInfo reqInfo, long bytesWritten, long totalSize, double percent);

}

