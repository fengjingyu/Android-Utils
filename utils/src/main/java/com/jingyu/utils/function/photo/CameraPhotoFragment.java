package com.jingyu.utils.function.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.jingyu.utils.application.PlusFragment;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilBitmap;
import com.jingyu.utils.util.UtilDate;

import java.io.File;
import java.util.Date;

/**
 * @author fengjingyu@foxmail.com
 * @description 从摄像头获取图片
 */
public class CameraPhotoFragment extends PlusFragment {
    // 打开摄像头的请求码
    public static final int CAMERA_REQUEST_CODE = 0;
    // 裁剪的请求码
    public static final int RESIZE_REQUEST_CODE = 1;
    // 权限的请求码
    public static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2;
    // 拍照的图片
    private File cameraOutputFile;
    // 拍照后裁剪的图片
    private File cropOutputFile;
    // 存储图片的文件夹
    private File savePhotoDir;
    // 是否允许裁剪图片，默认为不允许
    private boolean isResize;
    // 裁剪图片是返回bitmap还是uri
    private CropReturnPattern cropReturnPattern = CropReturnPattern.URI;
    private Handler handler = new Handler(Looper.getMainLooper());

    enum CropReturnPattern {
        BITMAP, URI
    }

    public interface OnCameraSelectedFileListener {
        void onCameraSelectedFile(File file);
    }

    private OnCameraSelectedFileListener listener;

    public void setOnCameraSelectedFileListener(OnCameraSelectedFileListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (isPermissionGranted(Manifest.permission.CAMERA)) {
            openCamera();
        } else {
            permissionRequest(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.i(this + "--onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Logger.shortToast("请到设置界面打开拍照权限");
                }
            }
            break;
        }
    }

    private void openCamera() {
        // 连续拍照时需重置
        cameraOutputFile = null;
        cropOutputFile = null;
        if ((cameraOutputFile = createCameraOutputFile()) != null && cameraOutputFile.exists()) {
            try {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraOutputFile));
                cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.shortToast("打开拍照失败");
            }
        } else {
            Logger.shortToast("打开拍照失败");
        }
    }

    private void openCrop(Uri uri) {
        try {
            if (uri != null && (cropOutputFile = createCropOutputFile()) != null && cropOutputFile.exists()) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("crop", "true");
                // 裁剪框的比例，1:1
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                // 裁剪后输出图片尺寸的大小
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                //图片格式
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                //是否将数据保留在Bitmap中返回
                if (cropReturnPattern == CropReturnPattern.BITMAP) {
                    intent.putExtra("return-data", true);
                } else {
                    intent.putExtra("return-data", false);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropOutputFile));
                }
                startActivityForResult(intent, RESIZE_REQUEST_CODE);
            } else {
                Logger.shortToast("打开裁剪图片失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.shortToast("打开裁剪图片失败");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i(this + "--onActivityResult--" + (data == null ? "data为null" : ""));
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (isResize) {
                        openCrop(Uri.fromFile(cameraOutputFile));
                    } else {
                        if (listener != null) {
                            listener.onCameraSelectedFile(cameraOutputFile);
                        }
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    deleteCameraOutputFile();
                    if (cropReturnPattern == CropReturnPattern.BITMAP) {
                        if (data != null) {
                            Bundle bundle = data.getExtras();
                            if (bundle != null) {
                                Bitmap bitmap = bundle.getParcelable("data");
                                bitmap2File(bitmap);
                            }
                        }
                    } else {
                        if (listener != null) {
                            listener.onCameraSelectedFile(cropOutputFile);
                        }
                    }
                    break;
            }
        } else {
            deleteCameraOutputFile();
            deleteCropOutputFile();
        }
    }

    private void bitmap2File(final Bitmap bitmap) {
        if (bitmap != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final File file = UtilBitmap.compressBitmap(bitmap, cropOutputFile, 100);
                    if (file != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (listener != null) {
                                    listener.onCameraSelectedFile(file);
                                }
                            }
                        });
                    } else {
                        deleteCropOutputFile();
                    }
                    bitmap.recycle();
                }
            }).start();
        }
    }

    private void deleteCameraOutputFile() {
        if (cameraOutputFile != null && cameraOutputFile.exists()) {
            cameraOutputFile.delete();
        }
    }

    private void deleteCropOutputFile() {
        if (cropOutputFile != null && cropOutputFile.exists()) {
            cropOutputFile.delete();
        }
    }

    private File createCameraOutputFile() {
        return DirHelper.createFile(getPhotoDir(), "camera_" + getTime() + ".jpg");
    }

    private File createCropOutputFile() {
        return DirHelper.createFile(getPhotoDir(), "camera_crop" + getTime() + ".jpg");
    }

    public File getPhotoDir() {
        File dir = DirHelper.createDir(savePhotoDir);
        if (dir != null) {
            return dir;
        } else {
            if (getActivity() != null) {
                // 相机可能无法吸入内部的存储
                return savePhotoDir = DirHelper.ExternalAndroid.getDir(getActivity(), Constants.DEFAULT_PHOTO_DIR_NAME);
            }
            return null;
        }
    }

    private String getTime() {
        return UtilDate.format(new Date(), UtilDate.FORMAT_CREATE_FILE);
    }

    public void setCropReturnPattern(CropReturnPattern cropReturnPattern) {
        this.cropReturnPattern = cropReturnPattern;
    }

    public void setSavePhotoDir(File savePhotoDir) {
        this.savePhotoDir = savePhotoDir;
    }

    public void setResizeImage(boolean isResize) {
        this.isResize = isResize;
    }

}
