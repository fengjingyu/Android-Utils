package com.jingyu.android.middle;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.jingyu.android.init.R;

import java.io.File;

/**
 * glide
 */
@GlideModule
public class Image extends AppGlideModule {

    public static final int PLACE_HOLDER = R.mipmap.ic_launcher;
    public static final int ERROR = R.mipmap.ic_launcher_round;
    public static final int CROSS_FADE = 200;

    public static void initImage(Context context) {

    }

    public static void display(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .transition(new DrawableTransitionOptions().crossFade(CROSS_FADE))
                .into(imageView);
    }

    public static void display(Context context, String url, ImageView imageView, int placeHolderImgId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeHolderImgId)
                .error(placeHolderImgId)
                .transition(new DrawableTransitionOptions().crossFade(CROSS_FADE))
                .into(imageView);
    }

    public static void displayCircle(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .transition(new DrawableTransitionOptions().crossFade(CROSS_FADE))
                .circleCrop()
                .into(imageView);
    }

    public static void displayRoundCorner(Context context, String url, ImageView imageView, int degree) {
        GlideApp.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .transition(new DrawableTransitionOptions().crossFade(CROSS_FADE))
                .transform(new RoundedCorners(degree))
                .into(imageView);
    }

    public static void displayNoCache(Context context, String url, ImageView target) {
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .transition(new DrawableTransitionOptions().crossFade(CROSS_FADE))
                .into(target);
    }

    public static void displayGiftAsBitmap(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);
    }

    /**
     * 只有加载Gif时才能加载成功
     */
    public static void displayGifOnly(Context context, String url, ImageView imageView, int erroId) {
        GlideApp.with(context)
                .asGif()
                .load(url)
                .error(erroId)
                .into(imageView);
    }

    /**
     * 显示本地视频(网络视频无效)
     */
    public static void displayLocalVideo(Context context, String filePath, ImageView imageView) {
        GlideApp.with(context)
                .load(Uri.fromFile(new File(filePath)))
                .into(imageView);
    }

    public void clearCache(final Context context) {
        clearMemoryCache(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clearDiskCache(context);
            }
        }).start();
    }

    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    public void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

    //-----------------------------------------↓↓AppGuideModule的配置-----------------------------
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        //获取内存的默认配置
        //MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        //int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        //int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        //int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        //builder.setMemoryCache(new LruResourceCache(1024*1024*24));//默认24m
        //builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        //builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        //builder.setDiskCache(new DiskLruCacheFactory());
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        //registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        //不使用清单配置的方式,减少初始化时间
        return false;
    }
    //------------------------------------------↑↑AppGuideModule的配置----------------------------

    /*
    //V4.0
    RequestOptions options = new RequestOptions();
    options.format(DecodeFormat.PREFER_ARGB_8888)
    options.centerCrop()//图片显示类型
    options.placeholder(loadingRes)//加载中图片
    options.error(errorRes)//加载错误的图片
    options.error(new ColorDrawable(Color.RED))//或者是个颜色值
    options.priority(Priority.HIGH)//设置请求优先级
    options.diskCacheStrategy(DiskCacheStrategy.ALL);
    options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)//仅缓存原图片
    options.diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
    options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)缓存缩略过的
    options.onlyRetrieveFromCache(true)//仅从缓存加载
    options.skipMemoryCache(true)//禁用缓存,包括内存和磁盘
    options.diskCacheStrategy(DiskCacheStrategy.NONE)仅跳过磁盘缓存
    options.override(200,200)加载固定大小的图片
    options.dontTransform()不做渐入渐出的转换
    options.transform(new RotateTransformation(180))//自定义旋转类
    options.transition(new DrawableTransitionOptions().dontTransition())//同上
    options.transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
    options.circleCrop()设置成圆形头像<这个是V4.0新增的>
    options.transform(new RoundedCorners(10))设置成圆角头像<这个是V4.0新增的>*/
}


///**
// * universalimageloader
// */
//public class Image {
//
//    private Image() {
//    }
//
//    public static void display(String url, ImageView imageview, DisplayImageOptions displayImageOptions, ImageLoadingListener imageLoadingListener) {
//        ImageLoader.getInstance().displayImage(url, imageview, displayImageOptions, imageLoadingListener);
//    }
//
//    public static void display(String url, ImageView imageview, DisplayImageOptions displayImageOptions) {
//        ImageLoader.getInstance().displayImage(url, imageview, displayImageOptions);
//    }
//
//    public static void display(String url, ImageView imageview) {
//        ImageLoader.getInstance().displayImage(url, imageview, defaultDisplayImageOptions);
//    }
//
//    public static ImageLoader getImageLoader() {
//        return ImageLoader.getInstance();
//    }
//
//    // TODO 修改默认图片
//    private static DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder()
//
//            .showImageOnLoading(R.mipmap.ic_launcher)
//
//            .showImageForEmptyUri(R.mipmap.ic_launcher)
//
//            .showImageOnFail(R.mipmap.ic_launcher)
//
//            .cacheInMemory(true)
//
//            .cacheOnDisc(true)
//
//            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//
//            .bitmapConfig(Bitmap.Config.RGB_565)
//
//            //.displayer(new FadeInBitmapDisplayer(0))//是否图片加载好后渐入的动画时间
//            //.displayer(new RoundedBitmapDisplayer(50))//圆形图片,这个不要与RoundImageView同时使用
//            .displayer(new SimpleBitmapDisplayer())
//
//            .build();
//
//    public static void initImage(Application application) {
//        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(application)
//
//                .memoryCacheExtraOptions(480, 800)
//
//                .threadPoolSize(3)
//
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//
//                .denyCacheImageMultipleSizesInMemory()
//
//                .memoryCache(new WeakMemoryCache())
//
//                .discCacheSize(50 * 1024 * 1024)
//
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//
//                .discCacheFileCount(500)
//
//                .discCache(new UnlimitedDiscCache(Config.getImageLoaderCacheDir(application)))
//
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//
//                .imageDownloader(new BaseImageDownloader(application, 5 * 1000, 30 * 1000)) // connectTimeout
//
//                .writeDebugLogs() // Remove for release app
//
//                .build());
//    }
//
//    public static DisplayImageOptions getOption(int loadingId) {
//        return new DisplayImageOptions.Builder()
//
//                .showImageOnLoading(loadingId) // 设置图片在下载期间显示的图片
//
//                .showImageForEmptyUri(loadingId)// 设置图片Uri为空或是错误的时候显示的图片
//
//                .showImageOnFail(loadingId) // 设置图片加载/解码过程中错误时候显示的图片
//
//                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
//
//                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
//
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
//
//                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//
//                // .displayer(new FadeInBitmapDisplayer(0))// 是否图片加载好后渐入的动画时间
//                // .displayer(new RoundedBitmapDisplayer(50)) // 圆形图片
//                .displayer(new SimpleBitmapDisplayer())
//
//                .build();
//    }
//
//}
