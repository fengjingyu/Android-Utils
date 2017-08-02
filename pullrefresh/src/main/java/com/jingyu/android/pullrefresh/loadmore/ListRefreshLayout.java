package com.jingyu.android.pullrefresh.loadmore;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * @description 封装了上下拉 ， 分页 ，无数据背景
 */
public class ListRefreshLayout extends RefreshLayout {

    public ListRefreshLayout(Context context) {
        super(context);
    }

    public ListRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initHeadStyle() {
        PtrUIHandler mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        mPtrRefreshLayout.setHeaderView((PtrClassicDefaultHeader) mPtrClassicHeader);
        mPtrRefreshLayout.addPtrUIHandler(mPtrClassicHeader);
    }
}
