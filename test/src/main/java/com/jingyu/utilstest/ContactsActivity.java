package com.jingyu.utilstest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jingyu.middle.Image;
import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.adapter.PlusAdapter;
import com.jingyu.utils.function.ExecutorManager;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilContacts;
import com.jingyu.utils.util.UtilView;

import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class ContactsActivity extends BaseActivity {
    private ListView contacts_listview;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initWidgets();
    }

    class ContactsAdapter extends PlusAdapter<UtilContacts.ContactBean> {

        public ContactsAdapter(Context context, List<UtilContacts.ContactBean> list) {
            super(context, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            UtilContacts.ContactBean bean = list.get(position);

            ContactViewHolder holder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_contact_item, null);
                holder = new ContactViewHolder();
                holder.textview = (TextView) convertView.findViewById(R.id.contact_item);
                holder.imageview = (ImageView) convertView.findViewById(R.id.contact_imageview);
                convertView.setTag(holder);

            } else {
                holder = (ContactViewHolder) convertView.getTag();
            }

            holder.textview.setText(bean.name + "--" + bean.email + "--" + bean.phone_number);
            Image.displayImage("http://www.baidu.com/img/bdlogo.png", holder.imageview);

            return convertView;

        }

        class ContactViewHolder {
            TextView textview;
            ImageView imageview;
        }
    }

    public void initWidgets() {
        contacts_listview = getViewById(R.id.contacts_list);
        UtilView.setListViewStyle(contacts_listview, null, 1, false);

        final Dialog dialog = new ProgressDialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        ExecutorManager.getCache().execute(new Runnable() {
            @Override
            public void run() {
                // 获取联系人
                final List<UtilContacts.ContactBean> list = UtilContacts.getContacts(getApplicationContext());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.i(list);

                        // 创建adapter
                        ContactsAdapter adpater = new ContactsAdapter(getActivity(), list);
                        // 设置adapter
                        contacts_listview.setAdapter(adpater);

                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, ContactsActivity.class));
    }
}
