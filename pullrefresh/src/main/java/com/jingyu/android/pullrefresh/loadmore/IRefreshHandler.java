package com.jingyu.android.pullrefresh.loadmore;

public interface IRefreshHandler {
    /**
     * 是否能够下拉刷新
     */
    boolean canRefresh();

    /**
     * 是否能够上拉加载
     */
    boolean canLoad();

    /**
     * 下拉刷新
     */
    void refresh(int page);

    /**
     * 上拉加载
     */
    void load(int page);

}
