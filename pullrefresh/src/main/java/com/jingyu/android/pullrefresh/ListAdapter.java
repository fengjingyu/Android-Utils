package com.jingyu.android.pullrefresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jingyu.android.pullrefresh.loadmore.PlusRecyclerAdapter;

import java.util.List;


/**
 * Created by xtyx_jy on 2017/4/20.
 */
public class ListAdapter extends PlusRecyclerAdapter<ItemBean.DataBean, ListAdapter.ListHolder> {

    public ListAdapter(List<ItemBean.DataBean> list) {
        super(list);
    }

    @Override
    public ListAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListHolder holder, int position) {
        ItemBean.DataBean dataBean = getList().get(position);
        holder.textView.setText(dataBean.getId());
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ListHolder(View convertView) {
            super(convertView);
            textView = (TextView) convertView.findViewById(R.id.text);
        }
    }

}
