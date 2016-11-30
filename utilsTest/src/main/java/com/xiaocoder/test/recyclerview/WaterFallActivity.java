package com.xiaocoder.test.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.xiaocoder.test.R;
import com.xiaocoder.test_middle.base.BaseActivity;
import com.xiaocoder.utils.function.helper.LogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @email fengjingyu@foxmail.com
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
        recyclerView.setAdapter(adapter = new WaterFallAdapter(getData()));

        // 添加 和 移除的默认动画效果
        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnClickAction(new WaterFallAdapter.OnClickAction() {
            @Override
            public void onClickAction(View view) {
                int position = (int) view.getTag();
                LogHelper.shortToast("onClickAction--" + position + "--" + adapter.getList().get(position));
            }

            @Override
            public void onLongClickAction(View view) {
                int position = (int) view.getTag();
                LogHelper.shortToast("onLongClickAction--" + position + "--" + adapter.getList().get(position));
            }
        });
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }


}
