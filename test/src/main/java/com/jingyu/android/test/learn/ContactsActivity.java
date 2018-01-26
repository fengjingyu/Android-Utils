package com.jingyu.android.test.learn;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jingyu.android.middle.AppImg;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.function.ThreadHelper;
import com.jingyu.utils.function.adapter.PlusAdapter;
import com.jingyu.utils.util.UtilView;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 */
public class ContactsActivity extends BaseActivity {
    private ListView contacts_listview;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initWidgets();

        if (isPermissionGranted(Manifest.permission.READ_CONTACTS)) {
            read();
        } else {
            permissionRequest(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    read();
                }
                break;
            default:
                break;
        }
    }

    class ContactsAdapter extends PlusAdapter<Contact> {

        public ContactsAdapter(Context context, List<Contact> list) {
            super(context, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            Contact bean = list.get(position);

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

            holder.textview.setText(bean.name + "@" + bean.phone_number);
            AppImg.display("http://www.baidu.com/img/bdlogo.png", holder.imageview);

            return convertView;

        }

        class ContactViewHolder {
            TextView textview;
            ImageView imageview;
        }
    }

    public static class Contact {
        public String name;
        public String phone_number;
    }

    public void initWidgets() {
        contacts_listview = getViewById(R.id.contacts_list);
        UtilView.setListViewStyle(contacts_listview, null, 1, false);
    }

    private void read() {
        final Dialog dialog = new ProgressDialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        ThreadHelper.getCache().execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                final List<Contact> list = new LinkedList<Contact>();

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Contact contact = new Contact();
                        contact.name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        contact.phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(contact);
                    }
                }

                // 获取联系人
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.d(list);

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
