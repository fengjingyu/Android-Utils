package com.jingyu.middle;

import android.widget.ImageView;

import com.jingyu.utils.imageloader.IImageLoader;

/**
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
