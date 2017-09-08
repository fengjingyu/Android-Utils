package com.jingyu.android.pullrefresh.loadmore;


import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class PlusRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> list;

    public PlusRecyclerAdapter(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public int getItemCount() {
        if (getList() == null) {
            return 0;
        }
        return getList().size();
    }
}
