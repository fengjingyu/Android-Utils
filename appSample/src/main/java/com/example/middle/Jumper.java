package com.example.middle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.app.MainActivity;

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

    public static void toMainActivity(Activity activityContext) {
        activityContext.startActivity(new Intent(activityContext, MainActivity.class));
    }

}
