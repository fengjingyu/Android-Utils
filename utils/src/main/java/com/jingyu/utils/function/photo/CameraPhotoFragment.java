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
public class CameraPhotoFragment extends PlusFragment {
    // 打开当地相册的请求码
    public static final int CAMERA_REQUEST_CODE = 0;
    // 裁剪的请求码
    public static final int RESIZE_REQUEST_CODE = 1;
    // 权限的请求码
    public static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2;
    // 图片文件
    private File tempFile;
    // 存储图片的文件夹
    private File savePhotoDir;
    // 是否允许裁剪图片，默认为不允许
    private boolean isResize;

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
        tempFile = DirHelper.createFile(getDir(), UUID.randomUUID().toString());
        if (tempFile == null) {
            Logger.shortToast("打开拍照失败");
            return;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i(this + "--onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    final Uri uri = Uri.fromFile(tempFile);//这个data为null
                    Logger.i(this + "--uri=" + uri);
                    if (isResize) {
                        requestResizeImage(uri);
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = UtilOom.getBitmapForLargeByUri(getActivity(), uri, 500, Bitmap.Config.RGB_565);
                                deleteTempFile();
                                getImage(bitmap);
                            }
                        }).start();
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    deleteTempFile();
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            final Bitmap bitmap = bundle.getParcelable("data");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getImage(bitmap);
                                }
                            }).start();
                        }
                    }
                    break;
            }
        }
    }

    public void requestResizeImage(Uri uri) {
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

    private void getImage(Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            if (bitmap != null) {
                final File file = new File(getDir(), "photo" + getTime() + ".jpg");
                fos = new FileOutputStream(file);
                //bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onCameraSelectedFile(file);
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != bitmap) {
                    bitmap.recycle();
                }
            }
        }
    }

    private void deleteTempFile() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
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
