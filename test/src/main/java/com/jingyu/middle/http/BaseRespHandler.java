package com.jingyu.middle.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.jingyu.test.maintab.MainActivity;
import com.jingyu.middle.App;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.http.DialogManager;
import com.jingyu.utils.http.IHttp.RespHandler;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.util.UtilDate;
import com.jingyu.utils.util.UtilString;
import com.jingyu.utils.util.UtilSystem;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jingyu.utils.function.helper.Logger.TAG_RESP_HANDLER;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseRespHandler<T> implements RespHandler<T> {

    private Activity activityContext;

    public BaseRespHandler(Activity activityContext) {
        super();
        this.activityContext = activityContext;
    }

    public BaseRespHandler() {
    }

    @Override
    public void onReadySendRequest(ReqInfo reqInfo) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onReadySendRequest()");

        setHttpHeaders(reqInfo);
        setSendTime(reqInfo);
        setHttpSecretParams(reqInfo);

        showDialog(reqInfo);
    }

    /**
     * 如果显示dialog，则isShowDialog为true 且 activityContext非空
     */
    private void showDialog(ReqInfo reqInfo) {
        if (activityContext != null && reqInfo.isShowDialog()) {
            DialogManager dialogManager = DialogManager.getInstance(activityContext);
            // TODO 设置dialog
            Dialog dialog = new SystemDialog(activityContext);
            addDialogListener(dialog);
            dialogManager.setDialog(dialog);
            dialogManager.mayShow(toString());
        }
    }

    /**
     * 服务端定义的成功状态码
     **/
    public static final String REQ_SUCCESS = "1";

    /**
     * 解析是否成功的规则，根据项目的json而定
     */
    @Override
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "---onMatchAppStatusCode()");

        //TODO 解析规则
        if (resultBean instanceof IHttpRespInfo) {

            if (UtilString.equals(((IHttpRespInfo) resultBean).getCode(), REQ_SUCCESS)) {
                return true;
            } else {
                statusCodeWrongLogic(resultBean);
                return false;
            }

        } else {
            Logger.e("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
            throw new RuntimeException("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
        }

    }

    public void statusCodeWrongLogic(T resultBean) {
        Logger.shortToast(((IHttpRespInfo) resultBean).getMsg());
    }

    @Override
    public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onSuccessButParseWrong()");
    }

    @Override
    public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onSuccessButCodeWrong()");
    }

    @Override
    public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onSuccessAll()");
    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onFailure()");

        Logger.shortToast("网络有误");
    }

    @Override
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onEnd()");
        closeDialog(reqInfo.isShowDialog());
    }

    private void closeDialog(boolean isShowDialog) {
        if (activityContext != null && isShowDialog) {
            DialogManager.getInstance(activityContext).mayClose(toString());
        }
    }

    public final void addDialogListener(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeDialog(true);
                    if (activityContext != null && !(activityContext instanceof MainActivity)) {
                        activityContext.finish();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 配置项目的请求头
     */
    public void setHttpHeaders(ReqInfo reqInfo) {
        //TODO 设置请求头
        Map<String, List<String>> map = new HashMap<>();
        map.put("_v", Arrays.asList(UtilSystem.getVersionCode(App.getApplication()) + ""));
        map.put("_p", Arrays.asList("1"));
        reqInfo.setHeadersMap(map);
    }

    /**
     * 配置项目的加密规则
     */
    public void setHttpSecretParams(ReqInfo reqInfo) {
        //TODO 设置加密
        if (reqInfo.isSecretParam()) {
            reqInfo.getFinalRequestParamsMap().put("testKey0", "java");
            reqInfo.getFinalRequestParamsMap().put("testKey3", "c");
            reqInfo.getFinalRequestParamsMap().put("testKey6", "c++");
        }
    }

    /**
     * 设置请求对象的生成时间
     */
    public void setSendTime(ReqInfo reqInfo) {
        long time = System.currentTimeMillis();
        reqInfo.setSendTime(time + Constants.COMMA_EN + UtilDate.format(new Date(time)));
    }

    @Override
    public void onProgressing(ReqInfo reqInfo, long bytesWritten, long totalSize, double percent) {
        Logger.i(TAG_RESP_HANDLER, this.toString() + "--onProgressing()");
    }
}
