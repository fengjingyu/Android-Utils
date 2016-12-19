package com.jingyu.utils.function.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingyu.utils.function.helper.Logger;

/**
 * @email fengjingyu@foxmail.com
 * @description 测试用的 , 直接创建一个listview , 然后 listview.setAdapter(new XCAdapterText(context,null);
 */
public class AdapterTest extends BAdapter<String> {

    public AdapterTest(Context context, List<String> data) {
        super(context, data);
        if (data == null) {
            list = new ArrayList<String>();
            String[] strs = {" 0 ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 00 ", " 11 ", " 22 ", " 33 ", " 44 ", " 55 ", " 66 ", " 77 ",
                    " 0 ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 000 ", " 111 ", " 222 ", " 333 ", " 444 ", " 555 ",
                    " 666 ", " 777 "};
            for (String str : strs) {
                list.add(str);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (list == null || list.size() == 0) {
            return null;
        }
        String bean = list.get(position);
        if (convertView == null) {
            Logger.i("convertView==null?--true" + position);
        } else {
            Logger.i("convertView==null?--false" + position + convertView.toString());
        }
        if (convertView == null) {
            TextView textView = new TextView(context);
            textView.setTextSize(22);
            textView.setText(bean);
            convertView = textView;
        } else {
            ((TextView) convertView).setText(bean);
        }
        return convertView;
    }
}
