package com.xiaocoder.utils.imageloader;

import android.widget.ImageView;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface IImageLoader {

    void display(String url, ImageView imageview, Object... obj);

    void display(String url, ImageView imageview);

}
