package com.jingyu.test;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.photo.AblumPhotoFragment;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class DestroyGCActivity extends BaseActivity {
    private int i = 10;
    private ImageView imageView;
    private AblumPhotoFragment ablumPhotoFragment;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destroygc);

        imageView = getViewById(R.id.gc_imageview);
        addFragment(R.id.gc_fragment, ablumPhotoFragment = new AblumPhotoFragment());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Logger.i(imageView);
                Logger.i(i);

                System.gc();
                System.gc();

                Logger.i(ablumPhotoFragment.toString());
                Logger.i(ablumPhotoFragment.getActivity());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Logger.i("handler --" + imageView); // 不为空
                        Logger.i("handler --" + i);// 不为空
                        Logger.i("handler --" + ablumPhotoFragment.toString()); // 不为空
                        Logger.i("handler --" + ablumPhotoFragment.getActivity()); // 空
                    }
                }, 10000);

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(this + "--onDestroy()");
    }

}
