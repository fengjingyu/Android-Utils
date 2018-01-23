package com.jingyu.android.test.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.jingyu.android.test.R;
import com.jingyu.utils.function.FileHelper;
import com.jingyu.utils.function.Logger;
import com.jingyu.utils.util.UtilSystem;

import java.io.File;
import java.io.IOException;

public class DirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir);
        //testAndroidApi();
        testInternal();
        testExternalAndroid();
        testAndroid();
        testlib();
    }

    private void testlib() {
        log("FileHelper.Internal.getFile(getApplicationContext(), \"libs\", \"abc\")", FileHelper.Internal.getFile(getApplicationContext(), "libs", "abc"), false);
        log("FileHelper.Internal.getFile(getApplicationContext(), \"\", \"libs\")", FileHelper.Internal.getFile(getApplicationContext(), "", "libs"), false);
        log("FileHelper.Internal.getDir(getApplicationContext(), \"libs/abc\")", FileHelper.Internal.getDir(getApplicationContext(), "libs/abc"), false);

        log("FileHelper.Internal.getFile(getApplicationContext(), \"lib\", \"abc\"", FileHelper.Internal.getFile(getApplicationContext(), "lib", "abc"), false);//权限问题?
        log("FileHelper.Internal.getFile(getApplicationContext(), \"\", \"lib\")", FileHelper.Internal.getFile(getApplicationContext(), "", "lib"), false);//权限问题?
        log("FileHelper.Internal.getDir(getApplicationContext(), \"lib/abc\"", FileHelper.Internal.getDir(getApplicationContext(), "lib/abc"), false);//权限问题?
    }

    private void testAndroid() {
        log("FileHelper.getAndroidDir(getApplicationContext(),\"dirdir_01\")", FileHelper.getAndroidDir(getApplicationContext(), "dirdir_01"), true);
        log("FileHelper.getAndroidDir(getApplicationContext(),\"dirdir_02/dirdir_03\")", FileHelper.getAndroidDir(getApplicationContext(), "dirdir_02/dirdir_03"), true);
        log("FileHelper.getAndroidDir(getApplicationContext(),null)", FileHelper.getAndroidDir(getApplicationContext(), null), true);

        log("FileHelper.getAndroidFile(getApplicationContext(),\"dirdir_04\",\"filefile_01\")", FileHelper.getAndroidFile(getApplicationContext(), "dirdir_04", "filefile_01"), false);
        log("FileHelper.getAndroidFile(getApplicationContext(),\"dirdir_05/dirdir_06\",\"filefile_02\"", FileHelper.getAndroidFile(getApplicationContext(), "dirdir_05/dirdir_06", "filefile_02"), false);
        log("FileHelper.getAndroidFile(getApplicationContext(),\"dirdir_07\"", FileHelper.getAndroidFile(getApplicationContext(), "dirdir_07", ""), false);
    }

    private void testInternal() {
        log("FileHelper.Internal.getDir(this, \"dir_01\")--", FileHelper.Internal.getDir(this, "dir_01"), true);
        log("FileHelper.Internal.getDir(this, \"dir_02/dir_03/dir_04\")", FileHelper.Internal.getDir(this, "dir_02/dir_03/dir_04"), true);
        log("FileHelper.Internal.getCacheDir(this)", FileHelper.Internal.getCacheDir(this), true);
        log("FileHelper.Internal.getFilesDir(this)", FileHelper.Internal.getFilesDir(this), true);
        log("FileHelper.Internal.getPackageDir(this)", FileHelper.Internal.getDir(this, ""), true);
        //log("FileHelper.Internal.getAppDir(this, \"dir_05\")", FileHelper.Internal.getAppDir(this, "dir_05"), true);
        log("FileHelper.Internal.getFile(this, \"dir_06\", \"file_01\")", FileHelper.Internal.getFile(this, "dir_06", "file_01"), false);
        log("FileHelper.Internal.getFile(this, \"dir_07/dir_8\", \"file_02\")", FileHelper.Internal.getFile(this, "dir_07/dir_08", "file_02"), false);
        log("FileHelper.Internal.getFile(this, \"lib\", \"file_03\")", FileHelper.Internal.getFile(this, "lib/dir_09/dir_10", "file_03"), false); // 没权限,异常;但有时可以创建,why?貌似如果系统已经创建了lib文件夹,好像我们就不能创建文件了
        log("FileHelper.createFile(FileHelper.Internal.getCacheDir(this), \"file_04\")", FileHelper.createFile(FileHelper.Internal.getCacheDir(this), "file_04"), false);
        log("FileHelper.Internal.getCacheFile(this, \"file_05\"), false)", FileHelper.Internal.getCacheFile(this, "file_05"), false);
        log("FileHelper.Internal.getFilesFile(this, \"file_06\")", FileHelper.Internal.getFilesFile(this, "file_06"), false);
    }

    private void testExternalAndroid() {
        log("FileHelper.ExternalAndroid.getPackageDir(this)", FileHelper.ExternalAndroid.getDir(this, ""), true);
        log("FileHelper.ExternalAndroid.getCacheDir(this)", FileHelper.ExternalAndroid.getCacheDir(this), true);
        log("FileHelper.ExternalAndroid.getFilesDir(this, \"\")", FileHelper.ExternalAndroid.getFilesDir(this, ""), true);
        log("FileHelper.ExternalAndroid.getFilesDir(this, \"dir_01\")", FileHelper.ExternalAndroid.getFilesDir(this, "dir_01"), true);
        log("FileHelper.ExternalAndroid.getFilesDir(this, \"dir_02/dir_03\")", FileHelper.ExternalAndroid.getFilesDir(this, "dir_02/dir_03"), true);
        log("FileHelper.ExternalAndroid.getDir(this, \"dir_04/dir_05\")", FileHelper.ExternalAndroid.getDir(this, "dir_04/dir_05"), true);
        log("FileHelper.ExternalAndroid.getFile(this, \"dir_06\", \"file_01\")", FileHelper.ExternalAndroid.getFile(this, "dir_06", "file_01"), true);
        log("FileHelper.ExternalAndroid.getFile(this, \"dir_07/dir_08\", \"file_02\")", FileHelper.ExternalAndroid.getFile(this, "dir_07/dir_08", "file_02"), true);
        log("FileHelper.createFile(FileHelper.ExternalAndroid.getCacheDir(this), \"file_03\")", FileHelper.createFile(FileHelper.ExternalAndroid.getCacheDir(this), "file_03"), true);
        log("FileHelper.ExternalAndroid.getCacheFile(this, \"file_04\"), false)", FileHelper.ExternalAndroid.getCacheFile(this, "file_04"), false);
        log("FileHelper.ExternalAndroid.getFilesFile(this, \"file_05\"), false)", FileHelper.ExternalAndroid.getFilesFile(this, "file_05"), false);
        log("FileHelper.ExternalAndroid.getFile(this, \"dir_09/dir_10\", \"file_06\")", FileHelper.ExternalAndroid.getFile(this, "dir_09/dir_10", "file_06"), false);
    }

    private void testFile() {
        File file = new File("C://123");
        boolean result = file.mkdirs();
        Logger.d(file + "--file.exists() =" + file.exists() + ",mkdirs()=" + result);//不会crash,但是文件不存在
        //C:/123--file.exists() =false,mkdirs()=false

        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        boolean result2 = file2.mkdirs();
        Logger.d(file2 + "--file2.exists() =" + file2.exists() + ",mkdirs()=" + result2);
        // /storage/emulated/0--file2.exists() =true,mkdirs()=false

        File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + System.currentTimeMillis());
        boolean result3 = file3.mkdirs();
        Logger.d(file3 + "--file3.exists() =" + file3.exists() + ",mkdirs()=" + result3);
        // /storage/emulated/0/1487840993165--file3.exists() =true,mkdirs()=true
    }

    private void testAndroidApi() {
        Logger.d(Environment.isExternalStorageEmulated() + "--ExternalStorageEmulated-" + UtilSystem.getPhoneBrand());
        //7.0小米5  true
        //6.0模拟器 true
        //4.3模拟器 false
        Logger.d(Environment.isExternalStorageRemovable() + "--ExternalStorageRemovable-" + UtilSystem.getPhoneBrand());
        //7.0小米5  false
        //6.0模拟器 false
        //4.3模拟器 false
        Logger.d(Environment.getDataDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /data
        //6.0模拟器  /data
        //5.1模拟器  /data
        //4.3模拟器  /data
        Logger.d(Environment.getDownloadCacheDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /cache
        //7.0小米5   /cache
        //5.1模拟器  /cache
        //4.3模拟器  /cache
        Logger.d(Environment.getExternalStorageDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0
        //6.0模拟器  /storage/emulated/0
        //5.1模拟器  /storage/sdcard
        //4.3模拟器  /storage/sdcard
        Logger.d(Environment.getExternalStoragePublicDirectory("") + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0
        //6.0模拟器  /storage/emulated/0
        //5.1模拟器  /storage/sdcard
        //4.3模拟器  /storage/sdcard
        Logger.d(Environment.getRootDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /system
        //6.0模拟器  /system
        //5.1模拟器  /system
        //4.3模拟器  /system
        Logger.d(getExternalCacheDir() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/cache
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/cache
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/cache
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/cache
        Logger.d(getExternalFilesDir("") + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/files
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/files
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/files
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/files
        Logger.d(getExternalFilesDir("ee") + "--" + UtilSystem.getPhoneBrand());//会创建目录,也可以创建多级目录
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/files/ee
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/files/ee
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ee
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ee
        Logger.d(getExternalFilesDir("ff/hh/ii") + "--" + UtilSystem.getPhoneBrand());//会创建目录,也可以创建多级目录
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/files/ff/hh/ii
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/files/ff/hh/ii
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ff/hh/ii
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ff/hh/ii
        Logger.d(getCacheDir() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /data/user/0/com.jingyu.test/cache
        //6.0模拟器  /data/user/0/com.jingyu.test/cache
        //5.1模拟器  /data/data/com.jingyu.test/cache
        //4.3模拟器  /data/data/com.jingyu.test/cache
        Logger.d(getFilesDir() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /data/user/0/com.jingyu.test/files
        //6.0模拟器  /data/user/0/com.jingyu.test/files
        //5.1模拟器  /data/data/com.jingyu.test/files
        //4.3模拟器  /data/data/com.jingyu.test/files
        Logger.d(getDir("dir1", Context.MODE_PRIVATE)); //会创建目录,且会加app_ //但是用该api不能带有path分隔符,即不能创建多级目录,否则异常 //如果需要创建多级目录,可以拿到该目录的路径然后用普通方法创建
        //7.0小米5   /data/user/0/com.jingyu.test/app_dir1
        //5.1模拟器  /data/data/com.jingyu.test/app_dir1
        Logger.d(getFileStreamPath("file1")); //不会创建文件,仅仅返回一个file的路径,如果该文件不存在,并不会创建,得手动创建 // 该api不能含有path分隔符,否则异常;这个getFileStreamPath
        //5.1模拟器  /data/data/com.jingyu.test/files/file1
        //7.0小米5   /data/user/0/com.jingyu.test/files/file1
        Logger.d(Environment.getExternalStoragePublicDirectory("testdir2/123").mkdirs() + "--" + UtilSystem.getPhoneBrand()); //不会创建目录,可以多级目录即可含有path分隔符, 但仅仅返回一个file的路径,如果该文件夹不存在,并不会创建,得手动创建
        Logger.d(getDir("", Context.MODE_PRIVATE));//会创建目录 /data/user/0/com.jingyu.test/app_
        Logger.d(getDir(null, Context.MODE_PRIVATE));//会创建目录/data/user/0/com.jingyu.test/app_null
    }

    public void log(String msg, File file, boolean isDir) {
        StringBuilder sb = new StringBuilder();
        sb.append(msg);
        if (file == null) {
            sb.append("file为null ,");
            Logger.d(sb.toString());
            return;
        } else {
            sb.append("file不为null ," + file.getAbsolutePath() + " , ");
        }

        if (file.exists()) {
            sb.append("file存在 ,");
            Logger.d(sb.toString());
            return;
        } else {
            sb.append("file不存在 ,");
        }

        if (isDir) {
            if (file.mkdirs()) {
                sb.append("file创建目录成功");
            } else {
                sb.append("file创建目录失败");
            }
        } else {
            try {
                if (file.createNewFile()) {
                    sb.append("file创建文件成功");
                } else {
                    sb.append("file创建文件失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
                sb.append("file创建件失败");
            }
        }
        Logger.d(sb.toString());
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, DirActivity.class));
    }
}
