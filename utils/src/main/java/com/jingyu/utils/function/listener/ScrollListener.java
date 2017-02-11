package com.jingyu.utils.function.listener;

import android.os.Parcelable;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * @author  fengjingyu@foxmail.com
 * @description
 */
public class ScrollListener implements OnScrollListener {

    private int visibleCount;
    private int start_index;
    private int end_index;
    private int totalCount;
    private Parcelable onSaveInstanceState;
    private boolean isScolling;

    public interface IScrollPositionListener {
        void whenTop();

        void whenBottom();

        void whenScroll();

        void whenIdle();
    }

    IScrollPositionListener mListener;

    public void setIScrollPositionLisener(IScrollPositionListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_FLING:
                isScolling = true;
                break;
            case OnScrollListener.SCROLL_STATE_IDLE:
                // 每当滑动停止的时候,保存listView的状态
                onSaveInstanceState = view.onSaveInstanceState();
                isScolling = false;
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                isScolling = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        start_index = firstVisibleItem; // 第一个是0
        visibleCount = visibleItemCount;
        totalCount = totalItemCount;
        end_index = firstVisibleItem + visibleItemCount - 1;

        if (mListener != null) {

            if (isBottom()) {
                mListener.whenBottom();
            }

            if (isTop()) {
                mListener.whenTop();
            }

            if (isScolling()) {
                mListener.whenScroll();
            } else {
                mListener.whenIdle();
            }
        }
    }

    public int get_start_index() {
        return start_index;
    }

    public int get_end_index() {
        return end_index;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isBottom() {
        return start_index + visibleCount >= totalCount;
    }

    public boolean isTop() {
        return start_index == 0;
    }

    public boolean isScolling() {
        return isScolling;
    }

    public Parcelable getOnSaveInstanceState() {
        return onSaveInstanceState;
    }
}

