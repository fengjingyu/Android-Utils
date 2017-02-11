package com.jingyu.utils.imageloader;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author fengjingyu@foxmail.com
 * @description universal-image-loader-1.9.1.jar
 */
public class UniversalImageLoader implements IImageLoader {
    protected ImageLoader mImageloader;
    /**
     * 默认的加载options
     */
    protected DisplayImageOptions mDefaultOptions;

    public ImageLoader getUniversalImageloader() {
        return mImageloader;
    }

    public UniversalImageLoader(ImageLoader imageLoader, DisplayImageOptions defaultOptions) {
        this.mImageloader = imageLoader;
        this.mDefaultOptions = defaultOptions;
    }

    @Override
    public void display(String url, ImageView imageview, Object... obj) {
        // TODO 指定配置,判断参数
        if (obj != null && obj.length > 0 && obj[0] instanceof DisplayImageOptions) {
            mImageloader.displayImage(url, imageview, (DisplayImageOptions) obj[0]);
        }
    }

    @Override
    public void display(String url, ImageView imageview) {
        mImageloader.displayImage(url, imageview, mDefaultOptions);
    }
}
