package com.jingyu.utils.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 * @description 获取通讯录
 */
public class UtilContacts {

    public static List<ContactBean> getContacts(Context context) {
        // 创建一个保存联系人的集合
        List<ContactBean> contact_list = new ArrayList<ContactBean>();
        ContentResolver resolver = context.getContentResolver();
        // raw_contact 表的uri
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        // data 表的uri
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                if (id != null) {
                    Cursor dataCursor = resolver.query(dataUri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{id}, null);
                    ContactBean contact_model = new ContactBean();
                    while (dataCursor.moveToNext()) {
                        String data = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                        String mimetype = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                        if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            contact_model.name = data;
                        } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            contact_model.phone_number = data;
                        } else if ("vnd.android.cursor.item/email_v2".equals(mimetype)) {
                            contact_model.email = data;
                        }
                    }
                    contact_list.add(contact_model);
                    dataCursor.close();
                }
            }
            cursor.close();
        }
        return contact_list;
    }

    public static class ContactBean implements Serializable {
        private static final long serialVersionUID = -4958724953107486903L;

        public String name;
        public String phone_number;
        public String email;
    }
}
