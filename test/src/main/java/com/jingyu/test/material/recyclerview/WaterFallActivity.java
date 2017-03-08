package com.jingyu.test.material.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.Logger;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class WaterFallActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private WaterFallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_water_view);

        recyclerView = getViewById(R.id.id_recyclerView);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter = new WaterFallAdapter(null));

        // 添加 和 移除的默认动画效果
        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnClickAction(new WaterFallAdapter.OnClickAction() {
            @Override
            public void onClickAction(View view) {
                int position = (int) view.getTag();
                Logger.shortToast("onClickAction--" + position + "--" + adapter.getList().get(position));
            }

            @Override
            public void onLongClickAction(View view) {
                int position = (int) view.getTag();
                Logger.shortToast("onLongClickAction--" + position + "--" + adapter.getList().get(position));
            }
        });
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, WaterFallActivity.class));
    }

}
