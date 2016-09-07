package com.xiaocoder.test_middle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xiaocoder.test.stack.SearchActivity;
import com.xiaocoder.test.stack.SearchActivity2;

/**
 * @author xiaocoder on 2016/9/7 14:43
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Jumper {

    public static void toActivity(Context context, Class<Activity> clazz) {
        Intent intent = new Intent(context, clazz);

        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    public static void toSearchActivity(Activity activityContext) {
        activityContext.startActivity(new Intent(activityContext, SearchActivity.class));
    }

    public static void toSearchActivity2(Activity activityContext) {
        activityContext.startActivity(new Intent(activityContext, SearchActivity2.class));
    }

}
