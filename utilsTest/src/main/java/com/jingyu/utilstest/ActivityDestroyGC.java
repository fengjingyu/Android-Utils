package com.jingyu.utilstest;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.photo.AblumPhotoFragment;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ActivityDestroyGC extends BaseActivity {
    private int i = 10;
    private ImageView id_gc_imageview;
    private AblumPhotoFragment ablumPhotoFragment;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_point);

        initWidgets();

        ablumPhotoFragment = new AblumPhotoFragment();
        addFragment(R.id.id_gc_fragment, ablumPhotoFragment);
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

                Logger.i(ablumPhotoFragment.toString());
                Logger.i(ablumPhotoFragment.getActivity());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Logger.i("handler --" + id_gc_imageview); // 不为空
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.i(this + "--onWindowFocusChanged");
    }
}
