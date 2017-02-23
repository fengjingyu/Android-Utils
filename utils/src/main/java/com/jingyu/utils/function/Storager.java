package com.jingyu.utils.function;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.jingyu.utils.util.UtilIo;
import com.jingyu.utils.util.UtilString;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.jingyu.utils.util.UtilIoAndr.createDirInside;

/**
 * @author fengjingyu@foxmail.com
 */
public class Storager {

    /**
     * 写文本到SD卡
     *
     * @param dirName  如"aa/bb" 在SDCard下建立/mnt/sdcard/aa/bb的目录,
     *                 如null 或 "" 在/mnt/sdcard的目录下建立文件
     * @param fileName 如"cc.txt", 不可以是"ee/cc.txt"
     */
    public static File write2SDCard(String dirName, String fileName, String content) {
        FileOutputStream fos = null;
        try {
            File file = createFileInSDCard(dirName, fileName);
            if (file == null) {
                return null;
            }
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从sd卡中读取数据
     *
     * @param dirName  如果是"" 或null 则从Environment.getExternalStorageState()目录读取文件
     * @param fileName
     */
    public static String readFromSDCard(String dirName, String fileName) {
        String result = null;
        if (isSDcardExist()) {
            try {
                FileInputStream fis = null;
                if (UtilString.isBlank(dirName)) {
                    fis = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + fileName);
                } else {
                    fis = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + dirName + File.separator + fileName);
                }
                result = UtilIo.toStringByInputStream(fis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 在sd卡中创建，如果没有sd卡， 则在内部存储中创建
     *
     * @param dirName "123","123/234","345/456/678","456/567/789.txt" (这个也是文件夹)
     *                <p/>
     *                会在 Environment.getExternalStorageDirectory()   + dirName下去建立目录
     */
    public static File createDirInAndroid(Context context, String dirName) {
        try {
            if (isSDcardExist()) {
                return createDirInSDCard(dirName);
            } else {
                return createDirInside(context, dirName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在android环境下的SDCard中创建文件夹,如果没有SDCard,则返回null
     *
     * @param dirName 如果传入的为 null或""或"   ",
     *                则返回的file为Environment.getExternalStorageState()目录 如果传入的为
     *                "aa/bb" 或"aa"
     *                则返回的是在Environment.getExternalStorageState()目录下创建aa/bb 或aa文件夹
     * @return
     */
    public static File createDirInSDCard(String dirName) {
        File dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (dirName == null || dirName.trim().length() == 0) {
                return Environment.getExternalStorageDirectory();// mnt/sdcard
            }
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + dirName;
            dir = new File(dirPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dir;
    }


    /**
     * 先在sd卡中创建，如果没有sd卡， 则在内部存储中创建
     *
     * @param dirName
     * @param fileName
     * @return
     */
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

    /**
     * 在SDCard中创建文件
     *
     * @param dirName  "aa/bb" "aa"都可,如果是null 或"" 默认为在Environment.getExternalStorageState()目录下创建文件
     * @param fileName 文件名-->"abc.txt"格式, 不可写成"abc/ed.txt"
     * @return 文件存在返回存在的文件, 不存在则创建后返回, 如果没有SD卡, 返回null
     */
    public static File createFileInSDCard(String dirName, String fileName) {
        try {
            File dir = createDirInSDCard(dirName);
            if (dir == null) {
                return null;
            }
            File file = new File(dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    public static boolean isSDcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File createDir(String dirAbsolutePath) {
        File file = new File(dirAbsolutePath);
        if (file.exists()) {
            return file;
        } else {
            if (file.mkdirs()) {
                return file;
            } else {
                return null;
            }
        }
    }

    public static File createFile(String fileAbsolutePath) {
        File file = new File(fileAbsolutePath);
        if (file.exists()) {
            return file;
        } else {
            try {
                if (file.createNewFile()) {
                    return file;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static File createFile(String dirAbsolutePath, String fileName) {
        if (dirAbsolutePath == null || dirAbsolutePath.trim().length() == 0) {
            return null;
        }

        if (fileName == null || fileName.trim().length() == 0) {
            return null;
        }

        return createFile(dirAbsolutePath + File.separator + fileName);
    }

    public static File createFile(File dir, String fileName) {
        if (dir != null) {
            return createFile(dir.getAbsolutePath(), fileName);
        } else {
            return null;
        }
    }

    /**
     * 外部存储--之android文件夹,会随app的删除而删除
     * storage/emulated/0/Android/data/<package>/files or cache
     * storage/sdcard/Android/data/<package>/files or cache
     */
    public static class ExternalAndroid {
        /**
         * @return Android/data/<package>/cache
         */
        public static File getCacheDir(Context context) {
            if (isSDcardExist()) {
                return context.getExternalCacheDir();
            } else {
                return null;
            }
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
            } else {
                return null;
            }
        }
    }

    /**
     * 外部存储--之公共目录,不会随app的删除而删除
     * /storage/emulated/0/
     * /storage/sdcard/
     */
    public static class ExternalPublic {
        /**
         * @return /storage/sdcard/
         */
        public static File getDir() {
            if (isSDcardExist()) {
                return Environment.getExternalStorageDirectory();
            } else {
                return null;
            }
        }

        /**
         * @param dirName "aa" ,"aa/bb"
         * @return /storage/sdcard/aa/bb
         */
        public static File getDir(String dirName) {
            if (isSDcardExist()) {
                File dir = Environment.getExternalStoragePublicDirectory(dirName);
                // 不会自动创建的,得调用mkdirs()
                dir.mkdirs();
                return dir;
            } else {
                return null;
            }
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
            if (fileName == null && fileName.trim().length() == 0) {
                return null;
            } else {
                File dir = getDir(context, dirName);
                if (dir != null) {
                    return createFile(dir, fileName);
                } else {
                    return null;
                }
            }
        }

        /**
         * @return dirName=null / "" / "  ",返回/data/data/<package>/
         * dirName="cc" 返回/data/data/<package>/cc
         * dirName="aa/bb" 返回/data/data/<package>/aa/bb
         */
        public static File getDir(Context context, String dirName) {
            if (dirName == null || dirName.trim().length() == 0) {
                return getDataDataPackageDir(context);
            } else {
                return createDir(getDataDataPackageDir(context).getAbsolutePath() + File.separator + dirName);
            }
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
        public static File getDataDataPackageDir(Context context) {
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
