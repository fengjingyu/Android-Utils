package com.jingyu.android.middle;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jingyu.android.init.R;

import java.io.File;

/**
 * Glide
 */
public final class Image2 {

    public static final int PLACE_HOLDER = R.mipmap.ic_launcher;
    public static final int ERROR = R.mipmap.ic_launcher_round;
    public static final int CROSS_FADE = 200;

    public static void display(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
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
        GlideApp.with(context).asBitmap().load(url).into(imageView);
    }

    /**
     * 只有加载Gif时才能加载成功
     */
    public static void displayGifOnly(Context context, String url, ImageView imageView, int erroId) {
        GlideApp.with(context).asGif().load(url).error(erroId).into(imageView);
    }

    /**
     * 显示本地视频(网络视频无效)
     */
    public static void displayLocalVidio(Context context, String filePath, ImageView imageView) {
        GlideApp.with(context).load(Uri.fromFile(new File(filePath))).into(imageView);
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


    /*  options.format(DecodeFormat.PREFER_ARGB_8888)
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
    private static RequestOptions getRequestOptions() {
        RequestOptions options = new RequestOptions();
        return options;
    }
}

