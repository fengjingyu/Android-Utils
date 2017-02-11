package com.jingyu.utilstest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.function.adapter.PlusAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class BaseAdapterActivity extends BaseActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseadapter);

        listview = getViewById(R.id.id_listview);
        listview.setAdapter(new DemoAdapter(this, getData()));
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    class DemoAdapter extends PlusAdapter<String> {

        public DemoAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String bean = list.get(position);

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.view_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textview.setText(bean);
            return convertView;
        }

        class ViewHolder {
            TextView textview;

            public ViewHolder(View view) {
                this.textview = (TextView) view.findViewById(R.id.id_content);
            }
        }
    }
}
