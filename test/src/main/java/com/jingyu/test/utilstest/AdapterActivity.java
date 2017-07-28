package com.jingyu.test.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.adapter.PlusAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 */
public class AdapterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrayadapter);

        ListView listview = getViewById(R.id.listview);
        // listview.setAdapter(new DemoAdapter(getActivity(), getData()));
        listview.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getData()));//simple_list_item_1系统自带的布局
        // listview.setAdapter(new TestAdapter(getActivity(), null));

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

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, AdapterActivity.class));
    }
}
