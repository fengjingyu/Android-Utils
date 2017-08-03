package com.jingyu.android.pullrefresh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.pullrefresh.loadmore.IRefreshHandler;
import com.jingyu.android.pullrefresh.loadmore.ListRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class AppMainActivity extends BaseActivity {
    ListRefreshLayout refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        final int num = 1;
        refreshView = getViewById(R.id.refreshList);
        //列表有多列的时候,这个放在设置adaper的前面,否则footview可能不会match屏幕宽度
        refreshView.setRecyclerLayoutManager(new GridLayoutManager(getActivity(), 2));
        refreshView.setRecyclerAdapter(new ListAdapter(null));
        refreshView.setDataZeroHint("", "", 0, "");
        refreshView.setHandler(new IRefreshHandler() {
            @Override
            public boolean canRefresh() {
                return true;
            }

            @Override
            public boolean canLoad() {
                return true;
            }

            @Override
            public void refresh(int requestPage) {
                request(requestPage);
            }

            @Override
            public void load(int requestPage) {
                request(requestPage);
            }
        });

    }

    public List<ItemBean.DataBean> getData(int page) {
        ItemBean data = new ItemBean();
        data.setCode("1");
        data.setMessage("msg");
        List<ItemBean.DataBean> list = new ArrayList<>();
        for (int i = 1; i <= page; i++) {
            list.add(new ItemBean.DataBean(page + "-page-" + i));
        }
        return list;
    }

    public void request(int page) {
        refreshView.setTotalPage(5);
        refreshView.updateAppend(getData(page));
        refreshView.completeRefresh(true);
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, AppMainActivity.class));
    }

}
