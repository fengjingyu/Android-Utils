package com.jingyu.utilstest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.function.photo.AblumPhotoFragment;
import com.jingyu.utils.function.photo.CameraPhotoFragment;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class CamareActivity extends BaseActivity {
    private CameraPhotoFragment cameraPhotoFragment;
    private AblumPhotoFragment ablumPhotoFragment;
    private LinearLayout ablum_layout;
    private Button ablumNoCrop;
    private Button ablumCrop;
    private LinearLayout camare_layout;
    private Button caremaNoCrop;
    private Button caremaCrop;
    private ImageView imageview;


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

        ablum_layout = getViewById(R.id.ablum_layout);
        ablumNoCrop = getViewById(R.id.ablumNoCrop);
        ablumCrop = getViewById(R.id.ablumCrop);
        camare_layout = getViewById(R.id.camare_layout);
        caremaNoCrop = getViewById(R.id.caremaNoCrop);
        caremaCrop = getViewById(R.id.caremaCrop);
        imageview = getViewById(R.id.imageview);

        addFragment(R.id.ablum_layout, ablumPhotoFragment);
        addFragment(R.id.camare_layout, cameraPhotoFragment);

    }

    public void setListeners() {
//        ablumNoCrop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ablumPhotoFragment.setIsAllowResizeImage(false);
//                ablumPhotoFragment.start();
//            }
//        });
//        ablumCrop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ablumPhotoFragment.setIsAllowResizeImage(true);
//                ablumPhotoFragment.start();
//            }
//        });

//        ablumPhotoFragment.setOnAblumSelectedFileListener(new AblumPhotoFragment.OnAblumSelectedFileListener() {
//
//            @Override
//            public void onLocalSelectedFile(File file) {
//                Logger.i(Uri.fromFile(file));
//                Logger.i(file.getAbsolutePath());
//                Logger.i(file.toURI());
//                imageview.setImageURI(Uri.fromFile(file));
//            }
//        });

        caremaCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPhotoFragment.setResizeImage(true);
                cameraPhotoFragment.start();
            }
        });

        caremaNoCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPhotoFragment.setResizeImage(false);
                cameraPhotoFragment.start();
            }
        });

        cameraPhotoFragment.setOnCameraSelectedFileListener(new CameraPhotoFragment.OnCameraSelectedFileListener() {

            @Override
            public void onCameraSelectedFile(File file) {
                Logger.i(Uri.fromFile(file));
                Logger.i(file.getAbsolutePath());
                Logger.i(file.toURI());
                imageview.setImageURI(Uri.fromFile(file));
            }
        });

    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, CamareActivity.class));
    }
}
