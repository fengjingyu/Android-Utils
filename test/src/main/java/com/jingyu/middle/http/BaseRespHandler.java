package com.jingyu.middle.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.jingyu.test.maintab.MainActivity;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.DialogManager;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;
import com.jingyu.utils.http.RespHandlerAdapter;
import com.jingyu.utils.util.UtilString;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class BaseRespHandler<T> extends RespHandlerAdapter<T> {

    private Activity activityContext;

    /**
     * 服务端定义的成功状态码
     **/
    //todo
    public static final String REQ_SUCCESS = "1";

    public BaseRespHandler(Activity activityContext) {
        super();
        this.activityContext = activityContext;
    }

    public BaseRespHandler() {
    }

    @Override
    public void onReadySendRequest(ReqInfo reqInfo) {
        super.onReadySendRequest(reqInfo);
        showDialog(reqInfo);
    }

    /**
     * 如果显示dialog，则isShowDialog为true 且 activityContext非空
     */
    private void showDialog(ReqInfo reqInfo) {
        if (activityContext != null && reqInfo.isShowDialog()) {
            DialogManager dialogManager = DialogManager.getInstance(activityContext);
            // TODO 设置dialog
            Dialog dialog = new HttpLoadingDialog(activityContext);
            addDialogListener(dialog);
            dialogManager.setDialog(dialog);
            dialogManager.mayShow(toString());
        }
    }

    /**
     * 解析是否成功的规则，根据项目的json而定
     */
    @Override
    public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        super.onMatchAppStatusCode(reqInfo, respInfo, resultBean);
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
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        super.onFailure(reqInfo, respInfo);
        Logger.shortToast("网络有误");
    }

    @Override
    public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
        super.onEnd(reqInfo, respInfo);
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
                    //todo
                    if (activityContext != null && !(activityContext instanceof MainActivity)) {
                        activityContext.finish();
                    }
                }
                return false;
            }
        });
    }

}
