package com.jingyu.android.test.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.takephoto.AblumPhotoPlusFragment;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
/**
 * @author fengjingyu@foxmail.com
 */
public class DestroyGCActivity extends BaseActivity {
    private int i = 10;
    private ImageView imageView;
    private AblumPhotoPlusFragment ablumPhotoFragment;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destroygc);

        imageView = getViewById(R.id.gc_imageview);
        addFragment(R.id.gc_fragment, ablumPhotoFragment = new AblumPhotoPlusFragment());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Logger.d(imageView);
                Logger.d(i);

                System.gc();
                System.gc();

                Logger.d(ablumPhotoFragment.toString());
                Logger.d(ablumPhotoFragment.getActivity());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Logger.d("handler --" + imageView); // 不为空
                        Logger.d("handler --" + i);// 不为空
                        Logger.d("handler --" + ablumPhotoFragment.toString()); // 不为空
                        Logger.d("handler --" + ablumPhotoFragment.getActivity()); // 空
                    }
                }, 10000);

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(this + "--onDestroy()");
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, DestroyGCActivity.class));
    }

}
