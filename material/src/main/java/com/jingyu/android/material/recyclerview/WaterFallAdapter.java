package com.jingyu.android.material.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jingyu.android.material.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author fengjingyu@foxmail.com
 */
public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.MyViewHolder> {

    public interface OnClickAction {
        void onClickAction(View view);

        void onLongClickAction(View view);
    }

    private OnClickAction onClickAction;

    public void setOnClickAction(OnClickAction onClickAction) {
        this.onClickAction = onClickAction;
    }

    private List<String> list;

    public WaterFallAdapter(List<String> list) {
        if (list == null){
            list = getData();
        }
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void addData(int position) {
        list.add(position, "Insert One");
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public WaterFallAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_waterfall, parent, false));
    }

    @Override
    public void onBindViewHolder(WaterFallAdapter.MyViewHolder holder, int position) {
        Random random = new Random();

        int i = random.nextInt(500);

        if (i < 100) {
            i = i + 150;
        }

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = i;
        // holder.itemView.setLayoutParams(layoutParams);

        holder.tv.setTag(position);
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.content);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickAction != null) {
                        onClickAction.onClickAction(v);
                    }
                }
            });

            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onClickAction != null) {
                        onClickAction.onLongClickAction(v);
                    }
                    return false;
                }
            });
        }
    }

    public List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }
}

