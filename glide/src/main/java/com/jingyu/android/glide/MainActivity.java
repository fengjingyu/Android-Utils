package com.jingyu.android.glide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.jingyu.android.middle.GlideApp;
import com.jingyu.android.middle.base.BaseActivity;

import java.io.File;

public class MainActivity extends BaseActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token();
        target();

    }

    private void token() {
        //url中含有token的方案
        Glide.with(getActivity()).load(new MyGlideUrl("http://baidu.com?token=123456"));
    }

    private void target() {
        //SimpleTarget,是一种极为简单的Target，使用它可以将Glide加载出来的图片对象获取到，而不是像之前那样只能将图片在ImageView上显示出来。
        //GlideDrawableImageViewTarget被限定只能作用在ImageView上，而ViewTarget的功能更加广泛，它可以作用在任意的View上
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                //imageView.setImageBitmap(resource);
            }
        };
        Glide.with(this).load("http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg").into(simpleTarget);
    }

    //需要注意的是，如果使用了preload()方法，最好要将diskCacheStrategy的缓存策略指定成DiskCacheStrategy.SOURCE。因为preload()方法默认是预加载的原始图片大小，
    //而into()方法则默认会根据ImageView控件的大小来动态决定加载图片的大小。因此，如果不将diskCacheStrategy的缓存策略指定成DiskCacheStrategy.SOURCE的话，
    //很容易会造成我们在预加载完成之后再使用into()方法加载图片，却仍然还是要从网络上去请求图片这种现象。调用了预加载之后，我们以后想再去加载这张图片就会非常快了

    public void preload() {
        GlideApp.with(this)
                .load("url")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload();
    }

    //其中downloadOnly(int width, int height)是用于在子线程中下载图片的，而downloadOnly(Y target)是用于在主线程中下载图片的。
    public void download() {
        downloadImage(imageView);
        Glide.with(getActivity()).downloadOnly().load("url");
    }

    @Deprecated
    public void downloadImage(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg";
                    final Context context = getApplicationContext(); //这个时候不能再用Activity作为Context了，因为会有Activity销毁了但子线程还没执行完这种可能出现
                    FutureTarget<File> target = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    // http://blog.csdn.net/guolin_blog/article/details/54895665
    //在不做代码处理的情况下,不管我们传入的是一张普通图片，还是一张GIF图片，Glide都会自动进行判断，并且可以正确地把它解析并展示出来。
    //如果必须是静态图片,可以用asBitmap();如果指定了只能加载动态图片，而传入的图片却是一张静图的话,就会加载失败


/*    DiskCacheStrategy.NONE： 表示不缓存任何内容。
    DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
    DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
    DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。*/

/*    // 加载本地图片
    File file = new File(getExternalCacheDir() + "/image.jpg");
    Glide.with(this).load(file).into(imageView);

    // 加载应用资源
    int resource = R.drawable.image;
    Glide.with(this).load(resource).into(imageView);

    // 加载二进制流
    byte[] image = getImageBytes();
    Glide.with(this).load(image).into(imageView);

    // 加载Uri对象
    Uri imageUri = getImageUri();
    Glide.with(this).load(imageUri).into(imageView);*/

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
    options.skipMemoryCache(true)//禁用内存缓存
    options.diskCacheStrategy(DiskCacheStrategy.NONE)仅跳过磁盘缓存
    options.override(200,200)加载固定大小的图片
    options.dontTransform()不做渐入渐出的转换
    options.transform(new RotateTransformation(180))//自定义旋转类
    options.transition(new DrawableTransitionOptions().dontTransition())//同上
    options.transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
    options.circleCrop()设置成圆形头像<这个是V4.0新增的>
    options.transform(new RoundedCorners(10))设置成圆角头像<这个是V4.0新增的>*/
}
