package com.xiaocoder.utils.imageloader;

import android.widget.ImageView;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface XCIImageLoader {

    void display(String url, ImageView imageview, Object... obj);

    void display(String url, ImageView imageview);

}
