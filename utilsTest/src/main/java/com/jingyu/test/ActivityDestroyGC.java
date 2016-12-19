package com.jingyu.test;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.jingyu.test_middle.base.BaseActivity;
import com.jingyu.utils.function.helper.LogHelper;
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

                LogHelper.i(id_gc_imageview);
                LogHelper.i(i);

                System.gc();
                System.gc();

                LogHelper.i(localPhotoFragment.toString());
                LogHelper.i(localPhotoFragment.getActivity());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogHelper.i("handler --" + id_gc_imageview); // 不为空
                        LogHelper.i("handler --" + i);// 不为空

                        LogHelper.i("handler --" + localPhotoFragment.toString()); // 不为空
                        LogHelper.i("handler --" + localPhotoFragment.getActivity()); // 空
                    }
                }, 10000);

            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.i(this + "--onDestroy()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogHelper.i(this + "--onWindowFocusChanged");
    }
}
