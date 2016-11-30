package com.example.middle.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.example.app.MainActivity;
import com.example.middle.App;
import com.xiaocoder.utils.function.Constants;
import com.xiaocoder.utils.function.helper.LogHelper;
import com.xiaocoder.utils.http.DialogManager;
import com.xiaocoder.utils.http.IHttp.RespHandler;
import com.xiaocoder.utils.http.ReqInfo;
import com.xiaocoder.utils.http.RespInfo;
import com.xiaocoder.utils.util.UtilDate;
import com.xiaocoder.utils.util.UtilString;
import com.xiaocoder.utils.util.UtilSystem;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @email fengjingyu@foxmail.com
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
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onReadySendRequest()");

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

            if (dialogManager.isDialogNull()) {
                // TODO 更新dialog
                // setDialogListener(DIALOG)
                // dialogManager.updateHttpDialog(DIALOG);
            }
            dialogManager.show(String.valueOf(this.hashCode()));
        }
    }

    /**
     * 后台成功的返回码
     **/
    public static final String REQ_SUCCESS = "1";

    /**
     * 解析是否成功的规则，根据项目的json而定
     */
    @Override
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "---onMatchAppStatusCode()");

        //TODO 解析规则
        if (resultBean instanceof IHttpRespInfo) {

            if (UtilString.equals(((IHttpRespInfo) resultBean).getCode(), REQ_SUCCESS)) {
                return true;
            } else {
                statusCodeWrongLogic(resultBean);
                return false;
            }

        } else {
            LogHelper.e("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
            throw new RuntimeException("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
        }

    }

    public void statusCodeWrongLogic(T resultBean) {
        LogHelper.shortToast(((IHttpRespInfo) resultBean).getMsg());
    }

    @Override
    public void onSuccessButParseWrong(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onSuccessButParseWrong()");
    }

    @Override
    public void onSuccessButCodeWrong(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onSuccessButCodeWrong()");
    }

    @Override
    public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onSuccessAll()");
    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onFailure()");

        LogHelper.shortToast("网络有误");
    }

    @Override
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onEnd()");
        closeDialog(reqInfo.isShowDialog());
    }

    private void closeDialog(boolean isShowDialog) {
        if (activityContext != null && isShowDialog) {
            DialogManager.getInstance(activityContext).close(String.valueOf(hashCode()));
        }
    }

    public final void setDialogListener(Dialog dialog) {

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    closeDialog(true);

                    if (!(activityContext instanceof MainActivity)) {
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
        map.put("_v", Arrays.asList(UtilSystem.getVersionCode(App.getAppContext()) + ""));
        map.put("_m", Arrays.asList(UtilSystem.getMacAddress(App.getAppContext())));
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
        LogHelper.i(Constants.TAG_RESP_HANDLER, this.toString() + "--onProgressing()");
    }

}
