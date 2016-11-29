package com.xiaocoder.utils.imageloader;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @email fengjingyu@foxmail.com
 * @description 第三方 universal-image-loader-1.9.0-with-sources.jar
 */
public class AsynLoader implements IImageLoader {

    /**
     * universal-image-loader-1.9.0-with-sources.jar 的图片加载
     */
    protected ImageLoader mImageloader;

    /**
     * 默认的加载options
     */
    protected DisplayImageOptions mDefaultOptions;

    public ImageLoader getmImageloader() {
        return mImageloader;
    }

    public AsynLoader(ImageLoader imageLoader, DisplayImageOptions defaultOptions) {
        this.mImageloader = imageLoader;
        this.mDefaultOptions = defaultOptions;
    }

    @Override
    public void display(String url, ImageView imageview, Object... obj) {
        // TODO 指定配置,判断参数
        if (obj[0] instanceof DisplayImageOptions) {
            mImageloader.displayImage(url, imageview, (DisplayImageOptions) obj[0]);
        }
    }

    @Override
    public void display(String url, ImageView imageview) {
        // 默认配置
        mImageloader.displayImage(url, imageview, mDefaultOptions);
    }
}
