package com.jingyu.test.maintab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.jingyu.test.R;
import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.exception.CrashHandler;
import com.jingyu.utils.exception.ExceptionDb;
import com.jingyu.utils.function.ActivityCollector;
import com.jingyu.utils.function.Logger;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends BaseActivity {

    public static final int CLICK_QUICK_GAP = 1000;
    //双击两次返回键退出应用
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
        addFragment(R.id.main_content, new OneFragment());
    }

    public void initWidgets() {
        tab_group = getViewById(R.id.main_tab_group);
    }

    public void setListeners() {
        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                hideAllFragment();

                switch (checkedId) {
                    case R.id.main_tab_item1:
                        showFragmentByClass(OneFragment.class, R.id.main_content);
                        break;
                    case R.id.main_tab_item2:
                        showFragmentByClass(TwoFragment.class, R.id.main_content);
                        break;
                    case R.id.main_tab_item3:
                        showFragmentByClass(ThreeFragment.class, R.id.main_content);
                        break;
                    case R.id.main_tab_item4:
                        showFragmentByClass(FourFragment.class, R.id.main_content);
                        break;
                    case R.id.main_tab_item5:
                        showFragmentByClass(FiveFragment.class, R.id.main_content);
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
            ActivityCollector.appExit();
        } else {
            back_quit_time = this_quit_time;
            Logger.shortToast("快速再按一次退出");
        }
    }

    /**
     * 进入首页时，把未上传的异常信息上传到服务器
     */
    protected void uploadException() {
        ExceptionDb exceptionModelDb = CrashHandler.getInstance().getExceptionDb();
        if (exceptionModelDb != null) {
            Logger.temp(exceptionModelDb.queryCount());
            Logger.temp(exceptionModelDb.queryUploadFail(ExceptionDb.SORT_DESC));
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, MainActivity.class));
    }
}
