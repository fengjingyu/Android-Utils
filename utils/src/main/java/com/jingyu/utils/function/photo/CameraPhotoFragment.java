package com.jingyu.utils.function.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.jingyu.utils.application.PlusFragment;
import com.jingyu.utils.function.Constants;
import com.jingyu.utils.function.DirHelper;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilDate;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private File caremaOutputFile;
    // 拍照后裁剪的图片
    private File cropOutputFile;
    // 存储图片的文件夹
    private File savePhotoDir;
    // 是否允许裁剪图片，默认为不允许
    private boolean isResize;
    // 裁剪图片是返回bitmap还是uri
    private CropReturnPattern cropReturnPattern = CropReturnPattern.URI;

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
                    Logger.shortToast("请到设置界面打开摄像头权限");
                }
            }
            break;
        }
    }

    public void openCamera() {
        if ((caremaOutputFile = getCameraOutputFile()) != null && caremaOutputFile.exists()) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caremaOutputFile));
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Logger.shortToast("打开拍照失败");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i(this + "--onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    Uri uri = Uri.fromFile(caremaOutputFile);//这个data为null
                    Logger.i(this + "--uri=" + uri);
                    if (isResize) {
                        if ((cropOutputFile = getCropOutputFile()) != null && cropOutputFile.exists()) {
                            requestResizeImage(uri);
                        } else {
                            Logger.shortToast("裁剪图片失败");
                        }
                    } else {
                        if (listener != null) {
                            listener.onCameraSelectedFile(caremaOutputFile);
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
                                getFile(bitmap);
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

    public void requestResizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片尺寸的大小, 如果output数值太大且return-data为true,可能会内存溢出
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        //图片格式
        intent.putExtra("outputFormat", "JPEG");
        //是否将数据保留在Bitmap中返回
        if (cropReturnPattern == CropReturnPattern.BITMAP) {
            intent.putExtra("return-data", true);
        } else {
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropOutputFile));
        }
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void getFile(final Bitmap bitmap) {
        if (bitmap != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedOutputStream bufferedOutputStream = null;
                    try {
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(cropOutputFile));
                        //bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
                        bufferedOutputStream.flush();
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (listener != null) {
                                        listener.onCameraSelectedFile(cropOutputFile);
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            bitmap.recycle();
                        }
                    }
                }
            }).start();
        }
    }

    private void deleteCameraOutputFile() {
        if (caremaOutputFile != null && caremaOutputFile.exists()) {
            caremaOutputFile.delete();
        }
    }

    private void deleteCropOutputFile() {
        if (cropOutputFile != null && cropOutputFile.exists()) {
            cropOutputFile.delete();
        }
    }

    private File getCameraOutputFile() {
        return DirHelper.createFile(getDir(), "photo" + getTime() + ".jpg");
    }

    private File getCropOutputFile() {
        return DirHelper.createFile(getDir(), cropReturnPattern + "_crop_photo" + getTime() + ".jpg");
    }

    private File getDir() {
        File dir = DirHelper.createDir(savePhotoDir);
        if (dir != null) {
            return dir;
        } else {
            if (getActivity() != null) {
                return savePhotoDir = DirHelper.getAndroidDir(getActivity(), Constants.DEFAULT_PHOTO_DIR_NAME);
            }
            return null;
        }
    }

    public void setCropReturnPattern(CropReturnPattern cropReturnPattern) {
        this.cropReturnPattern = cropReturnPattern;
    }

    private String getTime() {
        return UtilDate.format(new Date(), UtilDate.FORMAT_CREATE_FILE);
    }

    public void setSavePhotoDir(File savePhotoDir) {
        this.savePhotoDir = savePhotoDir;
    }

    public void setResizeImage(boolean isResize) {
        this.isResize = isResize;
    }

}
