package com.jingyu.utils.function.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jingyu.utils.R;
import com.jingyu.utils.application.PlusFragment;
import com.jingyu.utils.function.ExecutorManager;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilDate;
import com.jingyu.utils.util.UtilOom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author fengjingyu@foxmail.com
 * @description 从摄像头获取图片
 */
public class CameraPhotoFragment extends PlusFragment implements View.OnClickListener {
    // 打开当地相册的请求码
    public static final int CAMERA_REQUEST_CODE = 0;
    // 裁剪的请求码
    public static final int RESIZE_REQUEST_CODE = 1;

    private File tempPhotoFile;
    // 存储图片的文件夹
    private String savePhotoDir = "";
    // 是否允许裁剪图片，默认为不允许
    private boolean isAllowResize;

    private int imageId;
    private ImageView cameraImageView;

    private Handler handler = new Handler();

    public interface OnCaremaSelectedFileListener {
        void onCaremaSelectedFile(File file);
    }

    OnCaremaSelectedFileListener listener;

    public void setOnCaremaSelectedFileListener(OnCaremaSelectedFileListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.fragment_photo_camera, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidgets();
        listeners();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cameraImageView) {
            getTakePhoto();
        }
    }

    public void getTakePhoto() {
        if (isPermissionGranted(Manifest.permission.CAMERA)) {
            // 有权限
            todo();
        } else {
            permissionRequest(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSIONS);
        }
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        if (tempPhotoFile != null && tempPhotoFile.exists()) {
                            final Uri uri = Uri.fromFile(tempPhotoFile);
                            if (isAllowResize) {
                                resizeImage(uri);
                            } else {

                                ExecutorManager.getCache().execute(new Runnable() {
                                    Bitmap bitmap;

                                    @Override
                                    public void run() {
                                        bitmap = UtilOom.getBitmapForLargeByUri(getActivity(), uri, 500, Bitmap.Config.RGB_565);

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                getImage(bitmap);
                                                if (bitmap != null) {
                                                    bitmap.recycle();
                                                    bitmap = null;
                                                }
                                            }
                                        });
                                    }
                                });

                            }
                        } else {
                            Logger.shortToast("获取图片失败");
                        }
                    } else {
                        Logger.shortToast("未找到存储卡，无法存储照片！");
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    // 裁剪之后,关闭裁剪的activity后,会调用这个方法,
                    if (data != null) { // 加入data不等于空,即可以去到bitmap
                        getResizeImage(data);
                    }
                    break;
            }
        }
    }

    // 从这里获取最后返回的图片
    private void getImage(Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (tempPhotoFile != null && tempPhotoFile.exists()) {
                    tempPhotoFile.delete();
                }
                File file = new File(createDir(), "photo" + getTime() + ".jpg");
                fos = new FileOutputStream(file);
                //bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                if (listener != null) {
                    listener.onCaremaSelectedFile(file);
                }
            } else {
                Logger.shortToast("未检测到SD卡");
                if (listener != null) {
                    listener.onCaremaSelectedFile(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    if (null != bitmap) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            // 保存到本地
            FileOutputStream fos = null;
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if (tempPhotoFile != null && tempPhotoFile.exists()) {
                        tempPhotoFile.delete();
                    }
                    File file = new File(createDir(), "photo" + getTime() + ".jpg");
                    fos = new FileOutputStream(file);
                    //bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    if (listener != null) {
                        listener.onCaremaSelectedFile(file);
                    }
                } else {
                    Logger.shortToast("未检测到SD卡");
                    if (listener != null) {
                        listener.onCaremaSelectedFile(null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                        if (null != bitmap) {
                            bitmap.recycle();
                            bitmap = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setSavePhotoDir(String savePhotoDir) {
        this.savePhotoDir = savePhotoDir;
    }

    public File createDir() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + savePhotoDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public String getTime() {
        return UtilDate.format(new Date(), UtilDate.FORMAT_CREATE_FILE);
    }

    public void setImage(int drawableId) {
        this.imageId = drawableId;
        if (cameraImageView != null) {
            cameraImageView.setImageResource(drawableId);
        }
    }

    public void setIsAllowResizeImage(boolean isAllowResize) {
        this.isAllowResize = isAllowResize;
    }

    public void initWidgets() {
        cameraImageView = getViewById(R.id.cameraImageView);
        if (imageId > 0) {
            cameraImageView.setImageResource(imageId);
        }
    }

    public void listeners() {
        cameraImageView.setOnClickListener(this);
    }

    public void todo() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            tempPhotoFile = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString());
            if (!tempPhotoFile.exists()) {
                try {
                    tempPhotoFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.shortToast("创建文件失败");
                    return;
                }
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPhotoFile));
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Logger.shortToast("请插入sd卡");
        }
    }

    public static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Logger.i(this + "--onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSIONS: {
                if (permissions.length > 0 && Manifest.permission.CAMERA.equals(permissions[0])) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        todo();
                    } else {
                        Logger.shortToast("请到设置界面打开摄像头权限");
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
