package com.jingyu.android.pullrefresh.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import com.jingyu.android.common.util.ScreenUtil;
import com.jingyu.android.pullrefresh.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class MaterialRefreshLayout extends RefreshLayout {

    public MaterialRefreshLayout(Context context) {
        super(context);
    }

    public MaterialRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initHeadStyle() {
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, ScreenUtil.dip2px(getContext(), 15), 0, ScreenUtil.dip2px(getContext(), 10));
        header.setPtrFrameLayout(mPtrRefreshLayout);
        mPtrRefreshLayout.setHeaderView(header);
        mPtrRefreshLayout.addPtrUIHandler(header);
    }
}
