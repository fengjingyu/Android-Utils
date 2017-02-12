package com.jingyu.utilstest;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.function.photo.CameraPhotoFragment;
import com.jingyu.utils.function.photo.AblumPhotoFragment;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class CamareActivity extends BaseActivity {
    private CameraPhotoFragment cameraPhotoFragment;
    private AblumPhotoFragment ablumPhotoFragment;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camare);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        cameraPhotoFragment = new CameraPhotoFragment();
        ablumPhotoFragment = new AblumPhotoFragment();

        // cameraPhotoFragment.setIsAllowResizeImage(true);
        cameraPhotoFragment.setImage(R.mipmap.ic_launcher);
        ablumPhotoFragment.setImage(R.mipmap.ic_launcher);
        ablumPhotoFragment.setIsAllowResizeImage(true);

        imageView = getViewById(R.id.imageview);

        addFragment(R.id.ablum_layout, ablumPhotoFragment);
        addFragment(R.id.camare_layout, cameraPhotoFragment);

    }

    public void setListeners() {
        cameraPhotoFragment.setOnCaremaSelectedFileListener(new CameraPhotoFragment.OnCaremaSelectedFileListener() {

            @Override
            public void onCaremaSelectedFile(File file) {
                Logger.i(Uri.fromFile(file));
                Logger.i(file.getAbsolutePath());
                Logger.i(file.toURI());
                imageView.setImageURI(Uri.fromFile(file));
            }
        });

        ablumPhotoFragment.setOnLocalSelectedFileListener(new AblumPhotoFragment.OnLocalSelectedFileListener() {

            @Override
            public void onLocalSelectedFile(File file) {
                Logger.i(Uri.fromFile(file));
                Logger.i(file.getAbsolutePath());
                Logger.i(file.toURI());
                imageView.setImageURI(Uri.fromFile(file));
            }
        });
    }

}
