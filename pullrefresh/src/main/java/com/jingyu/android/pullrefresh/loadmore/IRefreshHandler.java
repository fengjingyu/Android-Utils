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
     * 1 处理获取到的数据
     * 2 调用refreshComplete()方法
     */
    void refresh(int requestPage);

    /**
     * 上拉加载
     */
    void load(int requestPage);

}
