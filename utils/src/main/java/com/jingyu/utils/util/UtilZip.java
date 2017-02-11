package com.jingyu.utils.util;

import android.content.Context;
import android.util.Log;

import com.jingyu.utils.encryption.rsa.Base64Helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author zhangpengfei.
 */
public class UtilZip {

    /**
     * 解压zip压缩文件到指定目录
     *
     * @param context         上下文对象
     * @param assetName       压缩文件名
     * @param outputDirectory 输出目录
     * @param isReWrite       是否覆盖
     */
    public static void unZip(Context context, String assetName, String outputDirectory, boolean isReWrite) {
        // 创建解压目标目录
        File file = new File(outputDirectory);
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        ZipInputStream zipInputStream = null;
        // 打开压缩文件
        try {
            inputStream = context.getAssets().open(assetName);
            zipInputStream = new ZipInputStream(inputStream);
            // 读取一个进入点
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            // 使用1Mbuffer
            byte[] buffer = new byte[1024 * 1024];
            // 解压时字节计数
            int count = 0;
            // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (zipEntry != null) {
                // 如果是一个目录
                if (zipEntry.isDirectory()) {
                    file = new File(outputDirectory + File.separator + zipEntry.getName());
                    // 文件需要覆盖或者是文件不存在
                    if (isReWrite || !file.exists()) {
                        file.mkdir();
                    }
                } else {
                    // 如果是文件
                    file = new File(outputDirectory + File.separator + zipEntry.getName());
                    // 文件需要覆盖或者文件不存在，则解压文件
                    if (isReWrite || !file.exists()) {
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        while ((count = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, count);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
                // 定位到下一个文件入口
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }

                if (null != zipInputStream) {
                    zipInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压zip压缩文件到指定目录
     *
     * @param context 上下文对象
     * @param in      输入目录
     * @param out     输出目录
     */
    public static void unzip(Context context, File in, String out, boolean isReWrite) {
        long extractedSize = 0L;
        if (null == in) {
            return;
        }
        File mOutput = new File(out);
        if (!mOutput.exists()) {
            if (!mOutput.mkdirs()) {
                Log.e("out", "Failed to make directories:" + mOutput.getAbsolutePath());
            }
        }
        Enumeration<ZipEntry> entries;
        ZipFile zip = null;
        try {
            zip = new ZipFile(in);
            entries = (Enumeration<ZipEntry>) zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                File destination = new File(mOutput, entry.getName());
                if (!destination.getParentFile().exists()) {
                    destination.getParentFile().mkdirs();
                }
                if (destination.exists() && context != null && !isReWrite) {
                }
                FileOutputStream outStream = new FileOutputStream(destination);
                extractedSize += copy(zip.getInputStream(entry), outStream);
                outStream.close();
            }
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != zip) {
                    zip.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[1024 * 8];
        BufferedInputStream in = new BufferedInputStream(input, 1024 * 8);
        BufferedOutputStream out = new BufferedOutputStream(output, 1024 * 8);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, 1024 * 8)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }

                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public static String compress(String src) {
        String result = null;
        try {
            if (src != null) {
                ByteArrayOutputStream bos = null;
                GZIPOutputStream gos = null;
                try {
                    bos = new ByteArrayOutputStream();
                    gos = new GZIPOutputStream(bos);

                    gos.write(src.getBytes());
                    gos.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (gos != null)
                            gos.close();
                        byte[] bytes = bos.toByteArray();
                        result = Base64Helper.encode(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (bos != null)
                            bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String deCompress(String compressSrc) {
        String result = null;

        try {
            if (compressSrc != null) {

                byte[] bytes = Base64Helper.decode(compressSrc);

                result = deCompress(bytes);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String deCompress(byte[] compressBytes) {
        return deCompress(compressBytes, "utf-8");
    }

    public static String deCompress(byte[] compressBytes, String encode) {

        String result = null;

        try {
            ByteArrayInputStream bis = null;
            GZIPInputStream gis = null;
            ByteArrayOutputStream bos = null;

            try {
                bis = new ByteArrayInputStream(compressBytes);
                gis = new GZIPInputStream(bis);
                bos = new ByteArrayOutputStream();

                byte[] buff = new byte[4096];
                int len;
                while ((len = gis.read(buff)) > 0) {
                    bos.write(buff, 0, len);
                }
                result = new String(bos.toByteArray(), encode);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null)
                        bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (gis != null)
                        gis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null)
                        bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
