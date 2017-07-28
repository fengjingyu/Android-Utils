package com.jingyu.android.middle;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jingyu.android.init.R;
import com.jingyu.android.middle.config.Config;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class Image {

    private Image() {
    }

    public static void displayImage(String url, ImageView imageview, Object... obj) {
        if (obj != null && obj.length > 0 && obj[0] instanceof DisplayImageOptions) {
            ImageLoader.getInstance().displayImage(url, imageview, (DisplayImageOptions) obj[0]);
        }
    }

    public static void displayImage(String url, ImageView imageview) {
        ImageLoader.getInstance().displayImage(url, imageview, getDefaultDisplayImageOptions());
    }

    public static DisplayImageOptions getDefaultDisplayImageOptions() {
        return defaultDisplayImageOptions;
    }

    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }

    // TODO 修改默认图片
    private static DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder()

            .showImageOnLoading(R.mipmap.ic_launcher)

            .showImageForEmptyUri(R.mipmap.ic_launcher)

            .showImageOnFail(R.mipmap.ic_launcher)

            .cacheInMemory(true)

            .cacheOnDisc(true)

            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)

            .bitmapConfig(Bitmap.Config.RGB_565)

            //.displayer(new FadeInBitmapDisplayer(0))//是否图片加载好后渐入的动画时间
            //.displayer(new RoundedBitmapDisplayer(50))//圆形图片,这个不要与RoundImageView同时使用
            .displayer(new SimpleBitmapDisplayer())

            .build();

    public static void initImage(Application application) {
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(application)

                .memoryCacheExtraOptions(480, 800)

                .threadPoolSize(3)

                .threadPriority(Thread.NORM_PRIORITY - 2)

                .denyCacheImageMultipleSizesInMemory()

                .memoryCache(new WeakMemoryCache())

                .discCacheSize(50 * 1024 * 1024)

                .discCacheFileNameGenerator(new Md5FileNameGenerator())

                .tasksProcessingOrder(QueueProcessingType.LIFO)

                .discCacheFileCount(500)

                .discCache(new UnlimitedDiscCache(Config.getImageLoaderCacheDir(application)))

                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())

                .imageDownloader(new BaseImageDownloader(application, 5 * 1000, 30 * 1000)) // connectTimeout

                .writeDebugLogs() // Remove for release app

                .build());
    }

    public static DisplayImageOptions getOption(int loadingId) {
        return new DisplayImageOptions.Builder()

                .showImageOnLoading(loadingId) // 设置图片在下载期间显示的图片

                .showImageForEmptyUri(loadingId)// 设置图片Uri为空或是错误的时候显示的图片

                .showImageOnFail(loadingId) // 设置图片加载/解码过程中错误时候显示的图片

                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中

                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中

                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示

                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//

                // .displayer(new FadeInBitmapDisplayer(0))// 是否图片加载好后渐入的动画时间
                .displayer(new SimpleBitmapDisplayer())
                // .displayer(new RoundedBitmapDisplayer(50)) // 圆形图片

                .build();// 构建完成
    }

}
