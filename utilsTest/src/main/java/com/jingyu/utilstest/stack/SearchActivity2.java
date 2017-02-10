package com.jingyu.utilstest.stack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jingyu.middle.Jumper;
import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utilstest.R;
import com.jingyu.utilstest.search.SearchRecordBean;
import com.jingyu.utilstest.search.SearchRecordDb;
import com.jingyu.utils.function.helper.Logger;

/**
 * @email fengjingyu@foxmail.com
 * @description 多个界面都有搜索时，每张表记录的信息类别是不一样的
 */
public class SearchActivity2 extends BaseActivity {

    // 搜索历史界面
    SearchRecordFragment record_fragment;
    // 搜索title
    TitleSearchFragment title_fragment;

    Button activity_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        title_fragment = new TitleSearchFragment();
        title_fragment.setDbParams(SearchRecordDb.TABLE_2);
        addFragment(R.id.xc_id_model_titlebar, title_fragment);

        activity_button = getViewById(R.id.activity_button);
    }

    public void setListeners() {
        // 点击edittext输入框 , 则弹出键盘和历史记录的背景
        title_fragment.setOnEditTextClicklistener(new TitleSearchFragment.OnEditTextClickedListener() {
            @Override
            public void clicked() {
                // 点击显示记录 , clicked()仅监听 从隐藏状态 -- > 显示状态

                // 为空则创建并设置监听 , record_fragment里面的监听器可以监听键盘的显示到隐藏的状态
                if (record_fragment == null) {
                    record_fragment = new SearchRecordFragment();
                    record_fragment.setDbParams(SearchRecordDb.TABLE_2);

                    // 点击键盘中的隐藏键盘按钮
                    record_fragment.setOnKeyBoardStatusListener(new SearchRecordFragment.OnKeyBoardStatusListener() {
                        @Override
                        public void onStatusChange(boolean is_key_board_show) {
                            // onStatusChange()仅监听 从显示状态 -- > 隐藏状态 , 关闭记录页面
                            if (!is_key_board_show) {
                                hideFragment(record_fragment);
                            }
                            Logger.shortToast("change");
                        }
                    });

                    record_fragment.setOnRecordItemClickListener(new SearchRecordFragment.OnRecordItemClickListener() {
                        @Override
                        public void onRecordItemClickListener(SearchRecordBean model, String key_word, int position) {
                            Logger.shortToast(key_word);
                            Jumper.toSearchActivity2(SearchActivity2.this);
                        }
                    });
                    addFragment(R.id.xc_id_model_content, record_fragment);
                    return;
                }
                // 不为空 , 则显示记录页面 ,隐藏搜索页面
                if (record_fragment.isHidden()) {
                    showFragment(record_fragment);
                }
            }
        });

        activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jumper.toSearchActivity(SearchActivity2.this);
            }
        });

        // 点击键盘的搜索按钮 , 会关闭键盘, 然后开启一个搜索结果的activity
        title_fragment.setOnPressSearchlistener(new TitleSearchFragment.OnKeyBoardSearchListener() {
            @Override
            public void searchKeyDown(String key_word) {
                Logger.shortToast(key_word);
                Jumper.toSearchActivity2(SearchActivity2.this);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(this, "onDestroy");
    }

    @Override
    public void finish() {
        super.finish();
        Logger.i(this, "finish");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Logger.i(this, "onBackPressed");
    }
}
