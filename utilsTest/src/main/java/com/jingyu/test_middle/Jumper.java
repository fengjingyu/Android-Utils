package com.jingyu.test_middle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jingyu.test.stack.SearchActivity;
import com.jingyu.test.stack.SearchActivity2;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Jumper {

    public static void toActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void toSearchActivity(Activity activityContext) {
        toActivity(activityContext, new Intent(activityContext, SearchActivity.class));
    }

    public static void toSearchActivity2(Activity activityContext) {
        toActivity(activityContext, new Intent(activityContext, SearchActivity2.class));
    }

}
