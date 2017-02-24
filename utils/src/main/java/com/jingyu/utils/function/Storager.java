package com.jingyu.utils.function;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author fengjingyu@foxmail.com
 */
public class Storager {

//    public static File write2SDCard(String dirName, String fileName, String content) {
//        FileOutputStream fos = null;
//        try {
//            File file = createFileInSDCard(dirName, fileName);
//            if (file == null) {
//                return null;
//            }
//            fos = new FileOutputStream(file);
//            fos.write(content.getBytes());
//            fos.flush();
//            fos.close();
//            return file;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static String readFromSDCard(String dirName, String fileName) {
//        String result = null;
//        if (isSDcardExist()) {
//            try {
//                FileInputStream fis = null;
//                if (UtilString.isBlank(dirName)) {
//                    fis = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + fileName);
//                } else {
//                    fis = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + dirName + File.separator + fileName);
//                }
//                result = UtilIo.toStringByInputStream(fis);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    public static File createDirInAndroid(Context context, String dirName) {
//        try {
//            if (isSDcardExist()) {
//                return createDirInSDCard(dirName);
//            } else {
//                return createDirInside(context, dirName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public static File createFileInAndroid(Context context, String dirName, String fileName) {
//        try {
//            if (isSDcardExist()) {
//                return createFileInSDCard(dirName, fileName);
//            } else {
//                return createFileInside(context, dirName, fileName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    //---------------------------------------------------------------------------------------------------------------------------
    public static boolean isSDcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isStringAvaliable(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static File createDir(File dir) {
        if (dir != null) {
            if (dir.exists() || dir.mkdirs()) {
                return dir;
            }
        }
        return null;
    }

    public static File createDir(String dirAbsolutePath) {
        if (isStringAvaliable(dirAbsolutePath)) {
            return createDir(new File(dirAbsolutePath));
        }
        return null;
    }

    public static File createFile(File file) {
        try {
            if (file != null) {
                if (file.exists() || file.createNewFile()) {
                    return file;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File createFile(String fileAbsolutePath) {
        if (isStringAvaliable(fileAbsolutePath)) {
            return createFile(new File(fileAbsolutePath));
        }
        return null;
    }

    public static File createFile(String dirAbsolutePath, String fileName) {
        if (isStringAvaliable(dirAbsolutePath) && isStringAvaliable(fileName)) {
            return createFile(dirAbsolutePath + File.separator + fileName);
        }
        return null;
    }

    public static File createFile(File dir, String fileName) {
        if (dir != null) {
            return createFile(dir.getAbsolutePath(), fileName);
        }
        return null;
    }

    /**
     * 外部存储--之android文件夹,会随app的删除而删除
     * storage/emulated/0/Android/data/<package>/files or cache
     * storage/sdcard/Android/data/<package>/files or cache
     */
    public static class ExternalAndroid {

        /**
         * @return fileName 为 null /"" /" ",返回null
         * fileName不为空:
         * dirName 为 null /"" /"  " 则在Android/data/package/创建文件
         * dirName 为"aa" 则在Android/data/package/aa创建文件
         * dirName 为"aa/bb" 则在Android/data/package/aa/bb创建文件
         */
        public static File getFile(Context context, String dirName, String fileName) {
            if (isStringAvaliable(fileName)) {
                return createFile(getDir(context, dirName), fileName);
            }
            return null;
        }

        /**
         * @return 如果sd卡可用
         * dirName=null / "" / "  ",返回Android/data/<package>/
         * dirName="cc" 返回Android/data/<package>/cc
         * dirName="aa/bb" 返回Android/data/<package>/aa/bb
         */
        public static File getDir(Context context, String dirName) {
            File packageDir = getPackageDir(context);
            if (packageDir != null) {
                if (isStringAvaliable(dirName)) {
                    return createDir(packageDir.getAbsolutePath() + File.separator + dirName);
                }
            }
            return packageDir;
        }

        /**
         * @return Android/data/<package>/cache
         */
        public static File getCacheDir(Context context) {
            if (isSDcardExist()) {
                return context.getExternalCacheDir();
            }
            return null;
        }

        /**
         * @return Android/data/<package>/files
         */
        public static File getFilesDir(Context context) {
            return getFilesDir(context, "");
        }

        /**
         * @param dirName "aa","aa/bb"
         * @return Android/data/<package>/files/aa/bb
         */
        public static File getFilesDir(Context context, String dirName) {
            if (isSDcardExist()) {
                return context.getExternalFilesDir(dirName);
            }
            return null;
        }

        /**
         * @return Android/data/<package>/
         */
        public static File getPackageDir(Context context) {
            if (isSDcardExist()) {
                File cacheDir = context.getExternalCacheDir();
                if (cacheDir != null) {
                    return cacheDir.getParentFile();
                }
            }
            return null;
        }
    }

    /**
     * 外部存储--之公共目录,不会随app的删除而删除
     * /storage/emulated/0/
     * /storage/sdcard/
     */
    public static class ExternalPublic {
        /**
         * @return fileName 为 null /"" /" ",返回null
         * fileName不为空:
         * dirName 为 null /"" /"  " 则在/storage/sdcard/创建文件
         * dirName 为"aa" 则在/storage/sdcard/aa创建文件
         * dirName 为"aa/bb" 则在/storage/sdcard/aa/bb创建文件
         */
        public static File getFile(String dirName, String fileName) {
            if (isStringAvaliable(fileName)) {
                return createFile(getDir(dirName), fileName);
            }
            return null;
        }

        /**
         * @return /storage/sdcard/
         */
        public static File getDir() {
            if (isSDcardExist()) {
                return Environment.getExternalStorageDirectory();
            }
            return null;
        }

        /**
         * @param dirName "aa" ,"aa/bb"
         * @return /storage/sdcard/aa/bb
         */
        public static File getDir(String dirName) {
            if (isSDcardExist()) {
                File dir = Environment.getExternalStoragePublicDirectory(dirName);
                // 不会自动创建的
                return createDir(dir);
            }
            return null;
        }
    }

    /**
     * 内部存储-会随app的删除而删除
     */
    public static class Internal {

        /**
         * @return fileName 为 null /"" /" ",返回null
         * fileName不为空:
         * dirName 为 null /"" /"  " 则在data/data/package/创建文件
         * dirName 为"aa" 则在data/data/package/aa创建文件
         * dirName 为"aa/bb" 则在data/data/package/aa/bb创建文件
         */
        public static File getFile(Context context, String dirName, String fileName) {
            if (isStringAvaliable(fileName)) {
                return createFile(getDir(context, dirName), fileName);
            }
            return null;
        }

        /**
         * @return dirName=null / "" / "  ",返回/data/data/<package>/
         * dirName="cc" 返回/data/data/<package>/cc
         * dirName="aa/bb" 返回/data/data/<package>/aa/bb
         */
        public static File getDir(Context context, String dirName) {
            if (isStringAvaliable(dirName)) {
                return createDir(getPackageDir(context).getAbsolutePath() + File.separator + dirName);
            }
            return getPackageDir(context);
        }

        /**
         * @param dirName 这个系统api只提供"aa" 或"bb"的目录 ,不可以是"aa/bb"带有分隔符的,否则非法参数异常;如果需要"aa/bb",可以获取路径后用普通方式创建多级目录
         * @return dirName为"" 返回/data/data/<package>/app_ ,这个app_是系统创建目录的时候自带的
         * dirName为null 返回/data/data/<package>/app_null,这个app_是系统创建目录的时候自带的
         * dirName非空 返回/data/data/<package>/app_+dirName,这个app_是系统创建目录的时候自带的
         */
        public static File getAppDir(Context context, String dirName) {
            return context.getDir(dirName, Context.MODE_PRIVATE);
        }

        /**
         * @return /data/data/<package>/cache
         */
        public static File getCacheDir(Context context) {
            return context.getCacheDir();
        }

        /**
         * @return /data/data/<package>/files
         */
        public static File getFilesDir(Context context) {
            return context.getFilesDir();
        }

        /**
         * @return /data/data/<package>/
         */
        public static File getPackageDir(Context context) {
            return context.getCacheDir().getParentFile();
        }

        /**
         * 写文本(覆盖之前的文件)到内部存储,文件保存在/data/data/"PACKAGE_NAME"/files/fileName
         *
         * @param fileName 如 "android.txt" ,不可以是"aa/bb.txt",即不可以包含路径分隔符
         */
        public static void write2FilesDirPrivate(Context context, String fileName, String content) {
            write2FilesDir(context, fileName, content, Context.MODE_PRIVATE);
        }

        /**
         * 写文本(在之前文本后面接着写)到内部存储,文件保存在/data/data/"PACKAGE_NAME"/files/fileName
         *
         * @param fileName 如 "android.txt" ,不可以是"aa/bb.txt",即不可以包含路径分隔符
         */
        public static void write2FilesDirAppend(Context context, String fileName, String content) {
            write2FilesDir(context, fileName, content, Context.MODE_APPEND);
        }

        private static void write2FilesDir(Context context, String fileName, String content, int mode) {
            FileOutputStream fos = null;
            BufferedWriter writer = null;
            try {
                fos = context.openFileOutput(fileName, mode);
                writer = new BufferedWriter(new OutputStreamWriter(fos));
                writer.write(content);
                //fos.write(content.getBytes());
                //fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 从内部存储读文件,文件在/data/data/"PACKAGE_NAME"/files/filename
         *
         * @param fileName 文件名 如 "android.txt" 不可以是"aa/bb.txt",系统只提供了"android.txt"方式的api
         */
        public static String readFromFilesDir(Context context, String fileName) {
            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();
            try {
                in = context.openFileInput(fileName);
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return content.toString();
        }

        /**
         * @return /system
         */
        public static File getSystemDir() {
            return Environment.getRootDirectory();
        }

        /**
         * @return /data
         */
        public static File getDataDir() {
            return Environment.getDataDirectory();
        }

        /**
         * @return /cache
         */
        public static File getCacheDir() {
            return Environment.getDownloadCacheDirectory();
        }
    }

    public static class IOStream {

        public static InputStream getInputStreamFromRaw(Context context, int rawId) {
            try {
                return context.getResources().openRawResource(rawId);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static InputStream getInputStreamFromAsserts(Context context, String fileName) {
            try {
                return context.getAssets().open(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static InputStream getInputStreamFromUri(Context context, Uri uri) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
