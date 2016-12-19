package com.jingyu.test;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.jingyu.test_middle.base.BaseActivity;
import com.jingyu.utils.function.helper.LogHelper;
import com.jingyu.utils.function.photo.CameraPhotoFragment;
import com.jingyu.utils.function.photo.LocalPhotoFragment;

import java.io.File;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class CamareActivity extends BaseActivity {
    CameraPhotoFragment camera_fragment;
    LocalPhotoFragment local_fragment;
    ImageView id_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camare);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        camera_fragment = new CameraPhotoFragment();
        local_fragment = new LocalPhotoFragment();

        // camera_fragment.setIsAllowResizeImage(true);
        camera_fragment.setImage(R.drawable.ic_launcher);
        local_fragment.setImage(R.drawable.ic_launcher);
        local_fragment.setIsAllowResizeImage(true);

        id_imageview = getViewById(R.id.id_imageview);

        addFragment(R.id.xc_id_fragment_test_local, local_fragment);
        addFragment(R.id.xc_id_fragment_test_camera, camera_fragment);

    }

    public void setListeners() {
        camera_fragment.setOnCaremaSelectedFileListener(new CameraPhotoFragment.OnCaremaSelectedFileListener() {

            @Override
            public void onCaremaSelectedFile(File file) {
                LogHelper.i(Uri.fromFile(file));
                LogHelper.i(file.getAbsolutePath());
                LogHelper.i(file.toURI());
                id_imageview.setImageURI(Uri.fromFile(file));
            }
        });

        local_fragment.setOnLocalSelectedFileListener(new LocalPhotoFragment.OnLocalSelectedFileListener() {

            @Override
            public void onLocalSelectedFile(File file) {
                LogHelper.i(Uri.fromFile(file));
                LogHelper.i(file.getAbsolutePath());
                LogHelper.i(file.toURI());
                id_imageview.setImageURI(Uri.fromFile(file));
            }
        });
    }

}
