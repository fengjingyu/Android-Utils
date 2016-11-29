package com.xiaocoder.utils.function.helper;

import java.io.Closeable;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public class CloseHelper {

    private CloseHelper() {
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
