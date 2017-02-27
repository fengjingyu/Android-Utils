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
 * @description 从相册获取图片
 */
public class AblumPhotoFragment extends PlusFragment {
    // 打开相册的请求码
    public static final int ABLUM_REQUEST_CODE = 3;
    // 裁剪的请求码
    public static final int RESIZE_REQUEST_CODE = 4;
    // 权限的请求码
    public static final int REQUEST_CODE_ABLUM_PERMISSIONS = 5;
    // 选中的相册文件
    private File ablumOutputFile;
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

    public interface OnAblumSelectedFileListener {
        void onAblumSelectedFile(File file);
    }

    private OnAblumSelectedFileListener listener;

    public void setOnAblumSelectedFileListener(OnAblumSelectedFileListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openAblum();
        } else {
            permissionRequest(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ABLUM_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.i(this + "--onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_ABLUM_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAblum();
                } else {
                    Logger.shortToast("请到设置界面打开写权限");
                }
            }
            break;
        }
    }

    private void openAblum() {
        try {
            Intent ablumIntent = new Intent();
            ablumIntent.setType("image/*");
            ablumIntent.setAction(Intent.ACTION_GET_CONTENT);
            ablumIntent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(ablumIntent, ABLUM_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.shortToast("打开相册失败");
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
                case ABLUM_REQUEST_CODE:
                    if (isResize) {
                        openCrop(data != null ? data.getData() : null);
                    } else {
                        if (listener != null) {
                            listener.onAblumSelectedFile(ablumOutputFile);
                        }
                    }
                    break;
                case RESIZE_REQUEST_CODE:
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
                            listener.onAblumSelectedFile(cropOutputFile);
                        }
                    }
                    break;
            }
        } else {
            deleteCropOutputFile();
        }
    }

    private void bitmap2File(final Bitmap bitmap) {
        if (bitmap != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedOutputStream bufferedOutputStream = null;
                    try {
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(cropOutputFile));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
                        bufferedOutputStream.flush();
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (listener != null) {
                                        listener.onAblumSelectedFile(cropOutputFile);
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

    private void deleteCropOutputFile() {
        if (cropOutputFile != null && cropOutputFile.exists()) {
            cropOutputFile.delete();
        }
    }

    private File createAblumOutputFile() {
        return DirHelper.createFile(getPhotoDir(), "ablum_" + getTime() + ".jpg");
    }

    private File createCropOutputFile() {
        return DirHelper.createFile(getPhotoDir(), cropReturnPattern + "_crop_ablum_" + getTime() + ".jpg");
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
