package com.jingyu.android.test.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.test.R;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.function.photo.AblumPhotoFragment;
import com.jingyu.utils.function.photo.CameraPhotoFragment;

import java.io.File;


/**
 * @author fengjingyu@foxmail.com
 */
public class CamareActivity extends BaseActivity {
    private CameraPhotoFragment cameraPhotoFragment;
    private AblumPhotoFragment ablumPhotoFragment;
    private Button ablumNoCrop;
    private Button ablumCrop;
    private Button caremaNoCrop;
    private Button caremaCrop;
    private ImageView imageview;
    private ImageView imageview_small;


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

        ablumNoCrop = getViewById(R.id.ablumNoCrop);
        ablumCrop = getViewById(R.id.ablumCrop);
        caremaNoCrop = getViewById(R.id.caremaNoCrop);
        caremaCrop = getViewById(R.id.caremaCrop);
        imageview = getViewById(R.id.imageview);
        imageview_small = getViewById(R.id.imageview_small);

        addFragment(R.id.ablum_layout, ablumPhotoFragment);
        addFragment(R.id.camare_layout, cameraPhotoFragment);

    }

    public void setListeners() {
        ablumNoCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ablumPhotoFragment.setResizeImage(false);
                ablumPhotoFragment.start();
            }
        });
        ablumCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ablumPhotoFragment.setResizeImage(true);
                ablumPhotoFragment.start();
            }
        });

        ablumPhotoFragment.setOnAblumListener(new AblumPhotoFragment.OnAblumListener() {
            @Override
            public void onPhotoSuccess(File originPhoto, File smallPhoto) {
                Logger.d(Uri.fromFile(smallPhoto));
                Logger.d(smallPhoto.getAbsolutePath());
                Logger.d(smallPhoto.toURI());
                imageview.setImageURI(Uri.fromFile(smallPhoto));

                imageview_small.setImageURI(Uri.fromFile(originPhoto));
            }
        });

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

        cameraPhotoFragment.setOnCameraListener(new CameraPhotoFragment.OnCameraListener() {
            @Override
            public void onPhotoSuccess(File originPhoto, File smallPhoto) {
                Logger.d(Uri.fromFile(smallPhoto));
                Logger.d(smallPhoto.getAbsolutePath());
                Logger.d(smallPhoto.toURI());
                imageview.setImageURI(Uri.fromFile(smallPhoto));
                imageview_small.setImageURI(Uri.fromFile(originPhoto));

            }
        });

    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, CamareActivity.class));
    }
}
