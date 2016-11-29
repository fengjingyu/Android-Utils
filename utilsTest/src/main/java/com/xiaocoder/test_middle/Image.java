package com.xiaocoder.test_middle;

import android.widget.ImageView;

import com.xiaocoder.utils.imageloader.IImageLoader;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Image {

    private static IImageLoader imageLoader;


    public static IImageLoader getImageLoader() {
        return imageLoader;
    }

    public static void initImager(IImageLoader imageLoader) {
        Image.imageLoader = imageLoader;
    }

    /**
     * 图片加载
     */
    public static void displayImage(String uri, ImageView imageView, Object... options) {
        imageLoader.display(uri, imageView, options);
    }

    public static void displayImage(String uri, ImageView imageView) {
        imageLoader.display(uri, imageView);
    }

}
