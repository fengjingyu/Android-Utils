package com.jingyu.android.pullrefresh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jingyu.android.middle.Http;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.middle.http.json.JsonModel;
import com.jingyu.android.middle.http.json.JsonRespHandler;
import com.jingyu.android.pullrefresh.loadmore.IRefreshHandler;
import com.jingyu.android.pullrefresh.loadmore.ListRefreshLayout;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.http.ReqInfo;
import com.jingyu.utils.http.RespInfo;

import java.util.ArrayList;
import java.util.List;

import static com.jingyu.android.pullrefresh.R.id.dataZeroLayout;
import static com.jingyu.android.pullrefresh.R.id.recyclerView;

public class ListActivity extends BaseActivity {
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
            public void refresh() {
                request(1);
            }

            @Override
            public void load() {
                request(2);
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

    public void request(final int page) {
        Logger.i("requestOldApi--" + page);
        Http.get("http://www.baidu.com", null, new JsonRespHandler() {

            @Override
            public JsonModel onParse2Model(ReqInfo reqInfo, RespInfo respInfo) {
                return new JsonModel();
            }

            @Override
            public boolean onMatchAppStatusCode(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                return true;
            }

            @Override
            public void onSuccessAll(ReqInfo reqInfo, RespInfo respInfo, JsonModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                refreshView.update(page, getData(page));
            }

            @Override
            public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);

            }

            @Override
            public void onEnd(ReqInfo reqInfo, RespInfo respInfo) {
                super.onEnd(reqInfo, respInfo);
                refreshView.completeRefresh();
            }
        });
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, ListActivity.class));
    }

}
