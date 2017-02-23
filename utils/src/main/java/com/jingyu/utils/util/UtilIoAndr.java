package com.jingyu.utils.util;

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
public class UtilIoAndr {

    public static boolean isSDcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static InputStream getInputStreamFromUri(Context context, Uri uri) {
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream getInputStreamFromRaw(Context context, int drawable_id) {
        try {
            return context.getResources().openRawResource(drawable_id);
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

    /**
     * 写文本到内部存储,文件保存在/data/data/"PACKAGE_NAME"/files/ 目录下
     *
     * @param fileName 如 "android.txt" ,不可以是"aa/bb.txt",即不可以包含路径
     * @param content
     * @param mode     模式 Context.MODE_APPEND/Context.MODE_PRIVATE
     */
    public static void write2Inside(Context context, String fileName, String content, int mode) {
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

    public static void write2InsideByPrivate(Context context, String fileName, String content) {
        write2Inside(context, fileName, content, Context.MODE_PRIVATE);
    }

    public static void write2InsideByAppend(Context context, String fileName, String content) {
        write2Inside(context, fileName, content, Context.MODE_APPEND);
    }

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
     * 从内部存储读文件,文件在/data/data/"PACKAGE_NAME"/files/filename
     *
     * @param fileName 文件名 如 "android.txt" 不可以是"aa/bb.txt",系统只提供了"android.txt"方式的api
     */
    public static String readFromInside(Context context, String fileName) {
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
     * 在android环境下的内部存储中的里创建文件夹
     *
     * @param dirName 如果传入的为 null或""或"   ", 则返回的file为context.getFilesDir()目录 如果传入的为
     *                "aa/bb" 或"aa" 则在context.getFilesDir()目录下创建aa/bb或aa文件夹
     * @return
     */
    public static File createDirInside(Context context, String dirName) {
        File dir = null;
        if (dirName == null || dirName.trim().length() == 0) {
            // return context.getCacheDir();// 内部存储下的/data/data/<package name>/cache
            return context.getFilesDir();
        }
        String dirPath = context.getFilesDir() + File.separator + dirName;
        dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
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
    public static File createFileInAndroid(Context context, String dirName, String fileName) {
        try {
            if (isSDcardExist()) {
                return createFileInSDCard(dirName, fileName);
            } else {
                return createFileInside(context, dirName, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    /**
     * 在内部存储中创建文件
     *
     * @param dirName  "aa/bb","aa"都可,如果是null 或""默认为在context.getFilesDir()目录下创建文件
     * @param fileName 文件名-->"abc.txt"格式, 不可写成"abc/ed.txt"
     * @return 文件存在返回存在的文件, 不存在则创建后返回,失败则返回null
     */
    public static File createFileInside(Context context, String dirName, String fileName) {
        try {
            File file = new File(createDirInside(context, dirName), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
