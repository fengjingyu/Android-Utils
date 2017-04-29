package com.jingyu.utils.util;

import java.io.Closeable;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class UtilClose {

    private UtilClose() {
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
