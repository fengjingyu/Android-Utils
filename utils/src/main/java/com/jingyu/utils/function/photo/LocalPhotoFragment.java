package com.jingyu.utils.function.photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jingyu.utils.function.helper.ExecutorManager;
import com.jingyu.utils.function.helper.Logger;
import com.jingyu.utils.util.UtilDate;
import com.jingyu.utils.R;
import com.jingyu.utils.application.PlusFragment;
import com.jingyu.utils.util.UtilOom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @email fengjingyu@foxmail.com
 * @description 打开本地相册
 */
public class LocalPhotoFragment extends PlusFragment implements View.OnClickListener {
    private ImageView xc_id_photo_local_imageview;
    public static final int LOCAL_IMAGE_REQUEST_CODE = 2;// 打开当地相册的请求码
    public static final int RESIZE_REQUEST_CODE = 3;// 裁剪的请求码
    public File temp_photo_file;

    public boolean is_allow_resize; // 是否允许裁剪图片，默认为不允许
    public int image_id;

    private Handler handler = new Handler();

    public interface OnLocalSelectedFileListener {
        void onLocalSelectedFile(File file);
    }

    OnLocalSelectedFileListener listener;

    public void setOnLocalSelectedFileListener(OnLocalSelectedFileListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.fragment_photo_local, container);
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
        if (id == R.id.xc_id_fragment_photo_local_imageview) {
            getLocalPhoto();
        }
    }

    public void getLocalPhoto() {
        checkPermission();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = getPath(getActivity(), uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
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
                case LOCAL_IMAGE_REQUEST_CODE:
                    if (data != null) {
                        if (is_allow_resize) {
                            resizeImage(data.getData());
                        } else {
                            final Uri uri = data.getData();
                            if (uri == null) {
                                Logger.shortToast("系统获取图片失败");
                                return;
                            }
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
                if (temp_photo_file != null && temp_photo_file.exists()) {
                    temp_photo_file.delete();
                }
                File file = new File(createDir(), "photo" + getTime() + ".jpg");
                fos = new FileOutputStream(file);
//                bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                if (listener != null) {
                    listener.onLocalSelectedFile(file);
                }
            } else {
                Logger.shortToast("未检测到SD卡");
                if (listener != null) {
                    listener.onLocalSelectedFile(null);
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
                    if (temp_photo_file != null && temp_photo_file.exists()) {
                        temp_photo_file.delete();
                    }
                    File file = new File(createDir(), "photo" + getTime() + ".jpg");
                    fos = new FileOutputStream(file);
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    if (listener != null) {
                        listener.onLocalSelectedFile(file);
                    }
                } else {
                    Logger.shortToast("未检测到SD卡");
                    if (listener != null) {
                        listener.onLocalSelectedFile(null);
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

    public String save_photo_dir = ""; // Constants.CHAT_PHOTO_FILE

    public void setSave_photo_dir(String save_photo_dir) {
        this.save_photo_dir = save_photo_dir;
    }


    public File createDir() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + save_photo_dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public String getTime() {
        return UtilDate.format(new Date(), UtilDate.FORMAT_FULL_S);
    }

    public void setImage(int drawable_id) {
        this.image_id = drawable_id;
        if (xc_id_photo_local_imageview != null) {
            xc_id_photo_local_imageview.setImageResource(drawable_id);
        }
    }

    public void setIsAllowResizeImage(boolean is_allow_resize) {
        this.is_allow_resize = is_allow_resize;
    }

    public void initWidgets() {
        xc_id_photo_local_imageview = getViewById(R.id.xc_id_fragment_photo_local_imageview);
        if (image_id > 0) {
            xc_id_photo_local_imageview.setImageResource(image_id);
        }
    }

    public void listeners() {
        xc_id_photo_local_imageview.setOnClickListener(this);
    }


    //以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否有权限
            int hasWRITE_EXTERNAL_STORAGEPermission = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasWRITE_EXTERNAL_STORAGEPermission != PackageManager.PERMISSION_GRANTED) {
                List<String> permissions = new ArrayList<String>();
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                // 请求获取权限
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            } else {
                // 有权限
                todo();
            }
        } else {
            //小于6.0
            todo();
        }
    }

    public static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                // 遍历请求权限的集合
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        // 用户允许
                        Logger.i("sinki ", "Permissions --> " + "Permission Granted: " + permissions[i]);
                        todo();
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // 用户拒绝
                        Logger.i("sinki ", "Permissions --> " + "Permission Denied: " + permissions[i]);
                        Logger.shortToast("请到设置界面打开相册权限");
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    private void todo() {
        Intent intentFromLocal = new Intent();
        intentFromLocal.setType("image/*"); // 设置文件类型
        intentFromLocal.setAction(Intent.ACTION_GET_CONTENT);
        intentFromLocal.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intentFromLocal, LOCAL_IMAGE_REQUEST_CODE);
    }
}
