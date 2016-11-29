package com.example.app;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.app.tab.FiveFragment;
import com.example.app.tab.TwoFragment;
import com.example.app.tab.OneFragment;
import com.example.app.tab.FourFragment;
import com.example.app.tab.ThreeFragment;
import com.example.middle.base.BaseActivity;
import com.xiaocoder.utils.application.BActivity;
import com.xiaocoder.utils.exception.CrashHandler;
import com.xiaocoder.utils.exception.ExceptionDb;
import com.xiaocoder.utils.function.helper.ActivityManager;
import com.xiaocoder.utils.io.LogHelper;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends BActivity {

    public static final int CLICK_QUICK_GAP = 1000;
    /**
     * 双击两次返回键退出应用
     */
    private long back_quit_time;

    private RadioGroup tab_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setListeners();
        setDatas();

    }

    private void setDatas() {
        addFragment(R.id.id_main_content, new OneFragment());
    }

    public void initWidgets() {
        tab_group = getViewById(R.id.id_main_tab_group);
    }

    public void setListeners() {
        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                hideAllFragment();

                switch (checkedId) {
                    case R.id.id_main_tab_item1:
                        showFragmentByClass(OneFragment.class, R.id.id_main_content);
                        break;
                    case R.id.id_main_tab_item2:
                        showFragmentByClass(TwoFragment.class, R.id.id_main_content);
                        break;
                    case R.id.id_main_tab_item3:
                        showFragmentByClass(ThreeFragment.class, R.id.id_main_content);
                        break;
                    case R.id.id_main_tab_item4:
                        showFragmentByClass(FourFragment.class, R.id.id_main_content);
                        break;
                    case R.id.id_main_tab_item5:
                        showFragmentByClass(FiveFragment.class, R.id.id_main_content);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        long this_quit_time = System.currentTimeMillis();
        if (this_quit_time - back_quit_time <= CLICK_QUICK_GAP) {
            ActivityManager.appExit();
        } else {
            back_quit_time = this_quit_time;
            LogHelper.shortToast("快速再按一次退出");
        }
    }

    /**
     * 进入首页时或闪屏页，把未上传的异常信息上传到服务器
     */
    protected void uploadException() {
        ExceptionDb exceptionModelDb = CrashHandler.getInstance().getExceptionModelDb();

        if (exceptionModelDb != null) {
            LogHelper.itemp(exceptionModelDb.queryCount());
            LogHelper.itemp(exceptionModelDb.queryUploadFail(ExceptionDb.SORT_DESC));
        }
    }
}
