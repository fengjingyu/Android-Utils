package com.jingyu.utils.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class UtilIo {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");// （windows:"\r\n"；unix:"\n"）； System.out.println()
    //public static final String PATH_SEPARATOR = System.getProperty("path.separator");// （windows:";"；unix:":"）；File.pathSeparator 环境变量
    //public static final String FILE_SEPARATOR = System.getProperty("file.separator");// （windows:"\"；unix:"/"）；File.separator  盘符

    @Nullable
    public static String getString(InputStreamReader inputStreamReader) {
        if (inputStreamReader == null) {
            return null;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static String getString(File file) {
        if (file == null) {
            return null;
        }

        try {
            return getString(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        return getString(new InputStreamReader(inputStream));
    }

    @Nullable
    public static byte[] getBytes(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            for (int len; (len = inputStream.read(buffer)) != -1; ) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean bytes2File(byte[] bytes, File toFile) {
        return bytes2File(bytes, toFile, false);
    }

    public static boolean bytes2File(byte[] bytes, File toFile, boolean append) {
        if (bytes == null || toFile == null) {
            return false;
        }

        try {
            return bytes2OutputStream(bytes, new FileOutputStream(toFile, append));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean string2OutputStream(String string, OutputStream outputStream) {
        if (string == null || outputStream == null) {
            return false;
        }

        return bytes2OutputStream(string.getBytes(), outputStream);
    }

    public static boolean bytes2OutputStream(byte[] bytes, OutputStream outputStream) {
        if (bytes == null || outputStream == null) {
            return false;
        }

        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean file2File(File fromFile, File toFile) {
        return file2File(fromFile, toFile, false);
    }

    public static boolean file2File(File fromFile, File toFile, boolean append) {
        if (fromFile == null || toFile == null) {
            return false;
        }

        try {
            return inputStream2OutputStream(new FileInputStream(fromFile), new FileOutputStream(toFile, append));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean inputStream2File(InputStream inputStream, File toFile) {
        return inputStream2File(inputStream, toFile, false);
    }

    public static boolean inputStream2File(InputStream inputStream, File toFile, boolean append) {
        if (inputStream == null || toFile == null) {
            return false;
        }

        try {
            return inputStream2OutputStream(inputStream, new FileOutputStream(toFile, append));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean inputStream2OutputStream(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            return false;
        }

        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            byte[] buf = new byte[8192];
            for (int len; (len = inputStream.read(buf)) != -1; ) {
                bufferedOutputStream.write(buf, 0, len);
                bufferedOutputStream.flush();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean printStream(InputStream inputStream, OutputStream outputStream) {
        return printStream(inputStream, outputStream, true);
    }

    public static boolean printStream(InputStream inputStream, OutputStream outputStream, boolean autoFlush) {
        if (inputStream == null || outputStream == null) {
            return false;
        }

        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(outputStream, autoFlush);
            for (String line; (line = reader.readLine()) != null; ) {
                writer.println(line);
            }

            return !writer.checkError();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nullable
    public static Object deepClone(Object obj) {
        if (obj == null) {
            return null;
        }

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nullable
    public static Object deserialization(File file) {
        if (file == null) {
            return null;
        }

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return objectInputStream.readObject();
        } catch (Exception e) {
            return null;
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean serialization(File file, Object obj) {
        if (file == null || obj == null) {
            return false;
        }

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(obj);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nullable
    public static List<File> getAllFilesByDirQueue(File dir) {
        if (dir != null) {
            LinkedList<File> result = new LinkedList<>();
            LinkedList<File> queue = new LinkedList<File>();
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    queue.addFirst(file);
                } else {
                    result.add(file);
                }
            }
            //遍历队列 子目录都在队列中
            while (!queue.isEmpty()) {
                //从队列中取出子目录
                File subDir = queue.removeLast();
                result.add(subDir);
                //遍历子目录。
                File[] subFiles = subDir.listFiles();
                // 如果目录是系统级文件夹，java没有访问权限，那么可能会返回null数组
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        //子目录中还有子目录，继续存到队列
                        if (subFile.isDirectory()) {
                            queue.addFirst(subFile);
                        } else {
                            result.add(subFile);
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }

    @Nullable
    public static List<File> getFilterFiles(File dir, List<File> list, FileFilter filter) {
        if (dir != null && list != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            // File[] files = dir.listFiles(filter);
            // 如果目录是系统级文件夹，java没有访问权限，那么会返回null数组
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        getFilterFiles(file, list, filter);
                    } else {
                        if (filter == null || filter.accept(file)) {
                            list.add(file);
                        }
                    }
                }
            }
        }
        return list;
    }

    @Nullable
    public static Properties getProperty(File configFile) {
        if (configFile == null) {
            return null;
        }

        FileReader fr = null;
        try {
            fr = new FileReader(configFile);
            Properties prop = new Properties();
            prop.load(fr);
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean saveProperty(File file, Properties properties, String desc) {
        if (file == null || properties == null) {
            return false;
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            //desc为注释,不要写中文
            properties.store(fw, desc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static InputStream getRawInputStream(Context context, int rawId) {
        try {
            if (context == null || rawId < 0) {
                return null;
            }
            return context.getResources().openRawResource(rawId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static InputStream getAssertInputStream(Context context, String fileName) {
        try {
            if (context == null || fileName == null) {
                return null;
            }
            return context.getAssets().open(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static InputStream getUriInputStream(Context context, Uri uri) {
        try {
            if (context == null || uri == null) {
                return null;
            }
            return context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从内部存的files文件夹里读取文件 即 /data/data/"PACKAGE_NAME"/files/filename
     *
     * @param fileName 文件名 如 "aa.txt" 不可以是"aa/bb.txt",这个openFileInput方法只提供了"aa.txt"方式,即不含文件分隔符
     */
    @Nullable
    public static String getInternalFilesString(Context context, String fileName) {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            return getString(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
