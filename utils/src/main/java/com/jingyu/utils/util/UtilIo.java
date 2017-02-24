package com.jingyu.utils.util;

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

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");// 换行符
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");// 如环境变量的路径分隔符 ; :
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");// 如c:/ 等同于File.separator

    @Nullable
    public static String getString(InputStreamReader inputStreamReader) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static String getString(File file) {
        try {
            return getString(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getString(InputStream inputStream) {
        return getString(new InputStreamReader(inputStream));
    }

    /**
     * 把输入流转为字节数组
     */
    @Nullable
    public static byte[] getBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[10240];
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

    public static boolean getFile(byte[] bytes, File toFile) {
        return getFile(bytes, toFile, false);
    }

    public static boolean getFile(byte[] bytes, File toFile, boolean append) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(toFile, append));
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

    public static boolean getFile(File fromFile, File toFile) {
        return getFile(fromFile, toFile, false);
    }

    public static boolean getFile(File fromFile, File toFile, boolean append) {
        try {
            return getFile(new FileInputStream(fromFile), toFile, append);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getFile(InputStream in, File toFile) {
        return getFile(in, toFile, false);
    }

    public static boolean getFile(InputStream inputStream, File toFile, boolean append) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(toFile, append));

            byte[] buf = new byte[10240];
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

    public static void printStream(InputStream inputStream, OutputStream outputStream) {
        printStream(inputStream, outputStream);
    }

    public static void printStream(InputStream inputStream, OutputStream outputStream, boolean append) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(outputStream, append);
            for (String line; (line = reader.readLine()) != null; ) {
                writer.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            //将对象写到流里
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);

            //从流里读出来
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

    /**
     * 以队列的方式获取目录里的文件
     */
    public static List<File> getAllFilesByDirQueue(File dir) {
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

    /**
     * 根据指定的过滤器在指定目录下获取所有的符合过滤条件的文件，并存储到list集合中
     */
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

    public static Properties getProperty(File configFile) {
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
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            properties.store(fw, desc);//desc为注释,不要写中文
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
}
