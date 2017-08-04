package com.jingyu.android.pullrefresh.loadmore;

import android.content.Context;
import android.util.AttributeSet;

public class MaterialPinRefreshLayout extends MaterialRefreshLayout {

    public MaterialPinRefreshLayout(Context context) {
        super(context);
    }

    public MaterialPinRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialPinRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initHeadStyle() {
        super.initHeadStyle();
        mPtrRefreshLayout.setPinContent(true);
    }
}
