package com.jingyu.utilstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.jingyu.test.R;
import com.jingyu.utils.function.DirHelper;
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
        testStoragerInternal();
        testStoragerExternalAndroid();
        testAndroid();
    }

    private void testAndroid() {
        log("DirHelper.getAndroidDir(getApplicationContext(),\"dirdir_01\")", DirHelper.getAndroidDir(getApplicationContext(), "dirdir_01"), true);
        log("DirHelper.getAndroidDir(getApplicationContext(),\"dirdir_02/dirdir_03\")", DirHelper.getAndroidDir(getApplicationContext(), "dirdir_02/dirdir_03"), true);
        log("DirHelper.getAndroidDir(getApplicationContext(),null)", DirHelper.getAndroidDir(getApplicationContext(), null), true);

        log("DirHelper.getAndroidFile(getApplicationContext(),\"dirdir_04\",\"filefile_01\")", DirHelper.getAndroidFile(getApplicationContext(), "dirdir_04", "filefile_01"), false);
        log("DirHelper.getAndroidFile(getApplicationContext(),\"dirdir_05/dirdir_06\",\"filefile_02\"", DirHelper.getAndroidFile(getApplicationContext(), "dirdir_05/dirdir_06", "filefile_02"), false);
        log("DirHelper.getAndroidFile(getApplicationContext(),\"dirdir_07\"", DirHelper.getAndroidFile(getApplicationContext(), "dirdir_07", ""), false);
    }

    private void testStoragerInternal() {
        log("DirHelper.Internal.getDir(this, \"dir_01\")--", DirHelper.Internal.getDir(this, "dir_01"), true);
        log("DirHelper.Internal.getDir(this, \"dir_02/dir_03/dir_04\")", DirHelper.Internal.getDir(this, "dir_02/dir_03/dir_04"), true);
        log("DirHelper.Internal.getCacheDir(this)", DirHelper.Internal.getCacheDir(this), true);
        log("DirHelper.Internal.getFilesDir(this)", DirHelper.Internal.getFilesDir(this), true);
        log("DirHelper.Internal.getPackageDir(this)", DirHelper.Internal.getPackageDir(this), true);
        log("DirHelper.Internal.getAppDir(this, \"dir_05\")", DirHelper.Internal.getAppDir(this, "dir_05"), true);
        log("DirHelper.Internal.getFile(this, \"dir_06\", \"file_01\")", DirHelper.Internal.getFile(this, "dir_06", "file_01"), false);
        log("DirHelper.Internal.getFile(this, \"dir_07/dir_8\", \"file_02\")", DirHelper.Internal.getFile(this, "dir_07/dir_08", "file_02"), false);
        log("DirHelper.Internal.getFile(this, \"lib\", \"file_03\")", DirHelper.Internal.getFile(this, "lib/dir_09/dir_10", "file_03"), false); // 没权限,异常;但有时可以创建,why?貌似如果系统已经创建了lib文件夹,好像我们就不能创建文件了
        log("DirHelper.createFile(DirHelper.Internal.getCacheDir(this), \"file_04\")", DirHelper.createFile(DirHelper.Internal.getCacheDir(this), "file_04"), false);
        log("DirHelper.Internal.getCacheFile(this, \"file_05\"), false)", DirHelper.Internal.getCacheFile(this, "file_05"), false);
        log("DirHelper.Internal.getFilesFile(this, \"file_06\")", DirHelper.Internal.getFilesFile(this, "file_06"), false);
    }

    private void testStoragerExternalAndroid() {
        log("DirHelper.ExternalAndroid.getPackageDir(this)", DirHelper.ExternalAndroid.getPackageDir(this), true);
        log("DirHelper.ExternalAndroid.getCacheDir(this)", DirHelper.ExternalAndroid.getCacheDir(this), true);
        log("DirHelper.ExternalAndroid.getFilesDir(this, \"\")", DirHelper.ExternalAndroid.getFilesDir(this, ""), true);
        log("DirHelper.ExternalAndroid.getFilesDir(this, \"dir_01\")", DirHelper.ExternalAndroid.getFilesDir(this, "dir_01"), true);
        log("DirHelper.ExternalAndroid.getFilesDir(this, \"dir_02/dir_03\")", DirHelper.ExternalAndroid.getFilesDir(this, "dir_02/dir_03"), true);
        log("DirHelper.ExternalAndroid.getDir(this, \"dir_04/dir_05\")", DirHelper.ExternalAndroid.getDir(this, "dir_04/dir_05"), true);
        log("DirHelper.ExternalAndroid.getFile(this, \"dir_06\", \"file_01\")", DirHelper.ExternalAndroid.getFile(this, "dir_06", "file_01"), true);
        log("DirHelper.ExternalAndroid.getFile(this, \"dir_07/dir_08\", \"file_02\")", DirHelper.ExternalAndroid.getFile(this, "dir_07/dir_08", "file_02"), true);
        log("DirHelper.createFile(DirHelper.ExternalAndroid.getCacheDir(this), \"file_03\")", DirHelper.createFile(DirHelper.ExternalAndroid.getCacheDir(this), "file_03"), true);
        log("DirHelper.ExternalAndroid.getCacheFile(this, \"file_04\"), false)", DirHelper.ExternalAndroid.getCacheFile(this, "file_04"), false);
        log("DirHelper.ExternalAndroid.getFilesFile(this, \"file_05\"), false)", DirHelper.ExternalAndroid.getFilesFile(this, "file_05"), false);
        log("DirHelper.ExternalAndroid.getFile(this, \"dir_09/dir_10\", \"file_06\")", DirHelper.ExternalAndroid.getFile(this, "dir_09/dir_10", "file_06"), false);
    }

    private void testFile() {
        File file = new File("C://123");
        boolean result = file.mkdirs();
        Logger.i(file + "--file.exists() =" + file.exists() + ",mkdirs()=" + result);//不会crash,但是文件不存在
        //C:/123--file.exists() =false,mkdirs()=false

        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        boolean result2 = file2.mkdirs();
        Logger.i(file2 + "--file2.exists() =" + file2.exists() + ",mkdirs()=" + result2);
        // /storage/emulated/0--file2.exists() =true,mkdirs()=false

        File file3 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + System.currentTimeMillis());
        boolean result3 = file3.mkdirs();
        Logger.i(file3 + "--file3.exists() =" + file3.exists() + ",mkdirs()=" + result3);
        // /storage/emulated/0/1487840993165--file3.exists() =true,mkdirs()=true
    }

    private void testAndroidApi() {
        Logger.i(Environment.isExternalStorageEmulated() + "--ExternalStorageEmulated-" + UtilSystem.getPhoneBrand());
        //7.0小米5  true
        //6.0模拟器 true
        //4.3模拟器 false
        Logger.i(Environment.isExternalStorageRemovable() + "--ExternalStorageRemovable-" + UtilSystem.getPhoneBrand());
        //7.0小米5  false
        //6.0模拟器 false
        //4.3模拟器 false
        Logger.i(Environment.getDataDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /data
        //6.0模拟器  /data
        //5.1模拟器  /data
        //4.3模拟器  /data
        Logger.i(Environment.getDownloadCacheDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /cache
        //7.0小米5   /cache
        //5.1模拟器  /cache
        //4.3模拟器  /cache
        Logger.i(Environment.getExternalStorageDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0
        //6.0模拟器  /storage/emulated/0
        //5.1模拟器  /storage/sdcard
        //4.3模拟器  /storage/sdcard
        Logger.i(Environment.getExternalStoragePublicDirectory("") + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0
        //6.0模拟器  /storage/emulated/0
        //5.1模拟器  /storage/sdcard
        //4.3模拟器  /storage/sdcard
        Logger.i(Environment.getRootDirectory() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /system
        //6.0模拟器  /system
        //5.1模拟器  /system
        //4.3模拟器  /system
        Logger.i(getExternalCacheDir() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/cache
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/cache
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/cache
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/cache
        Logger.i(getExternalFilesDir("") + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/files
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/files
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/files
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/files
        Logger.i(getExternalFilesDir("ee") + "--" + UtilSystem.getPhoneBrand());//会创建目录,也可以创建多级目录
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/files/ee
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/files/ee
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ee
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ee
        Logger.i(getExternalFilesDir("ff/hh/ii") + "--" + UtilSystem.getPhoneBrand());//会创建目录,也可以创建多级目录
        //7.0小米5   /storage/emulated/0/Android/data/com.jingyu.test/files/ff/hh/ii
        //6.0模拟器  /storage/emulated/0/Android/data/com.jingyu.test/files/ff/hh/ii
        //5.1模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ff/hh/ii
        //4.3模拟器  /storage/sdcard/Android/data/com.jingyu.test/files/ff/hh/ii
        Logger.i(getCacheDir() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /data/user/0/com.jingyu.test/cache
        //6.0模拟器  /data/user/0/com.jingyu.test/cache
        //5.1模拟器  /data/data/com.jingyu.test/cache
        //4.3模拟器  /data/data/com.jingyu.test/cache
        Logger.i(getFilesDir() + "--" + UtilSystem.getPhoneBrand());
        //7.0小米5   /data/user/0/com.jingyu.test/files
        //6.0模拟器  /data/user/0/com.jingyu.test/files
        //5.1模拟器  /data/data/com.jingyu.test/files
        //4.3模拟器  /data/data/com.jingyu.test/files
        Logger.i(getDir("dir1", Context.MODE_PRIVATE)); //会创建目录,且会加app_ //但是用该api不能带有path分隔符,即不能创建多级目录,否则异常 //如果需要创建多级目录,可以拿到该目录的路径然后用普通方法创建
        //7.0小米5   /data/user/0/com.jingyu.test/app_dir1
        //5.1模拟器  /data/data/com.jingyu.test/app_dir1
        Logger.i(getFileStreamPath("file1")); //不会创建文件,仅仅返回一个file的路径,如果该文件不存在,并不会创建,得手动创建 // 该api不能含有path分隔符,否则异常;这个getFileStreamPath
        //5.1模拟器  /data/data/com.jingyu.test/files/file1
        //7.0小米5   /data/user/0/com.jingyu.test/files/file1
        Logger.i(Environment.getExternalStoragePublicDirectory("testdir2/123").mkdirs() + "--" + UtilSystem.getPhoneBrand()); //不会创建目录,可以多级目录即可含有path分隔符, 但仅仅返回一个file的路径,如果该文件夹不存在,并不会创建,得手动创建
        Logger.i(getDir("", Context.MODE_PRIVATE));//会创建目录 /data/user/0/com.jingyu.test/app_
        Logger.i(getDir(null, Context.MODE_PRIVATE));//会创建目录/data/user/0/com.jingyu.test/app_null
    }

    public void log(String msg, File file, boolean isDir) {
        StringBuilder sb = new StringBuilder();
        sb.append(msg);
        if (file == null) {
            sb.append("file为null ,");
            Logger.i(sb.toString());
            return;
        } else {
            sb.append("file不为null ," + file.getAbsolutePath() + " , ");
        }

        if (file.exists()) {
            sb.append("file存在 ,");
            Logger.i(sb.toString());
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
        Logger.i(sb.toString());
    }

    public static void actionStart(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, DirActivity.class));
    }
}
