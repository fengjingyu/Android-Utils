package com.jingyu.test;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.jingyu.test_middle.base.BaseActivity;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.photo.LocalPhotoFragment;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ActivityDestroyGC extends BaseActivity {
    private int i = 10;
    private ImageView id_gc_imageview;
    private LocalPhotoFragment localPhotoFragment;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_point);

        initWidgets();

        localPhotoFragment = new LocalPhotoFragment();
        addFragment(R.id.id_gc_fragment, localPhotoFragment);
    }

    public void initWidgets() {

        id_gc_imageview = getViewById(R.id.id_gc_imageview);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 确保fragment已经添加了
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Logger.i(id_gc_imageview);
                Logger.i(i);

                System.gc();
                System.gc();

                Logger.i(localPhotoFragment.toString());
                Logger.i(localPhotoFragment.getActivity());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Logger.i("handler --" + id_gc_imageview); // 不为空
                        Logger.i("handler --" + i);// 不为空

                        Logger.i("handler --" + localPhotoFragment.toString()); // 不为空
                        Logger.i("handler --" + localPhotoFragment.getActivity()); // 空
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.i(this + "--onWindowFocusChanged");
    }
}
