package com.jingyu.utilstest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.jingyu.middle.base.BaseActivity;
import com.jingyu.test.R;
import com.jingyu.utils.function.IOHelper;
import com.jingyu.utils.function.Logger;

import java.io.InputStream;

public class BitmapActivity extends BaseActivity {

    ImageView imageInputStream;
    ImageView imageInputStreamResource;
    ImageView imageSetResource;
    ImageView imageMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        imageInputStream = getViewById(R.id.image_inputstream);
        imageInputStreamResource = getViewById(R.id.image_inputstream_resource);
        imageSetResource = getViewById(R.id.image_setresource);
        imageMatrix = getViewById(R.id.image_matrix);
        test5();

        test4();

        test3();

        test2();

        test();

    }

    private void test5() {
        //如果这个ic_launchar(72*72)是在hdpi(1)中的,在xhdpi(density 2)的手机里引用了
        InputStream rawInputStream = IOHelper.getRawInputStream(this, R.drawable.ic_launcher);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), rawInputStream);
        imageInputStreamResource.setImageDrawable(bitmapDrawable);
        Logger.i(bitmapDrawable.getBitmap().getWidth()); // 72
        Logger.i(bitmapDrawable.getBitmap().getHeight()); //72
    }

    private void test4() {
        InputStream rawInputStream = IOHelper.getRawInputStream(this, R.drawable.ic_launcher);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(rawInputStream);
        imageInputStream.setImageDrawable(bitmapDrawable);
        Logger.i(bitmapDrawable.getBitmap().getWidth()); // 72
        Logger.i(bitmapDrawable.getBitmap().getHeight()); //72
    }

    private void test3() {
        imageSetResource.setImageResource(R.drawable.ic_launcher);
        Logger.i(((BitmapDrawable) imageSetResource.getDrawable()).getBitmap().getHeight()); //144
        Logger.i(((BitmapDrawable) imageSetResource.getDrawable()).getBitmap().getWidth()); //144
    }

    private void test2() {
        InputStream rawInputStream = IOHelper.getRawInputStream(this, R.drawable.ic_launcher);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), rawInputStream);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Matrix matrix = new Matrix();
        matrix.preScale(1, 1, 0, 0);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Logger.i(result.getWidth() + "--" + result.getHeight());
        Logger.i(bitmap.getWidth() + "--" + bitmap.getHeight());
        Logger.i(bitmap.toString() + "--" + result.toString()); // 如果bitmap没变化,就是源对象返回,否则就是返回一个不同的bitmap对象
    }

    private void test() {
        InputStream rawInputStream = IOHelper.getRawInputStream(this, R.drawable.ic_launcher);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), rawInputStream);
        // BitmapDrawable bitmapDrawable = new BitmapDrawable(rawInputStream);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Logger.i("从inputStream读出来的bitmap--" + bitmap.getWidth() + "--" + bitmap.getHeight());
        Logger.i("从inputStream读出来的bitmapDrawable--" + bitmapDrawable.getIntrinsicWidth() + "--" + bitmapDrawable.getIntrinsicHeight());

        BitmapDrawable bitmapDrawable2 = new BitmapDrawable(getResources(), bitmap);
        Bitmap bitmap2 = bitmapDrawable2.getBitmap();
        Logger.i("从new BitmapDrawable(getResources(),bitmap);的bitmapDrawable--" + bitmapDrawable2.getIntrinsicWidth() + "--" + bitmapDrawable2.getIntrinsicHeight());

//        imageMatrix.setImageBitmap(bitmap);
//        imageMatrix.setImageBitmap(bitmap2);
//        imageMatrix.setImageDrawable(bitmapDrawable);
        imageMatrix.setImageDrawable(bitmapDrawable2);

        Logger.i("使用的bitmap--" + ((BitmapDrawable) imageMatrix.getDrawable()).getBitmap().getWidth() + "--" + ((BitmapDrawable) imageMatrix.getDrawable()).getBitmap().getHeight());
        Logger.i("使用的bitmapDrawable--" + ((BitmapDrawable) imageMatrix.getDrawable()).getIntrinsicWidth() + "--" + ((BitmapDrawable) imageMatrix.getDrawable()).getIntrinsicWidth());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.i("imageview--" + imageMatrix.getWidth() + "--" + imageMatrix.getHeight());
            }
        }, 1000);
    }

    private void test0() {
        InputStream rawInputStream = IOHelper.getRawInputStream(this, R.drawable.ic_launcher);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), rawInputStream);
        // BitmapDrawable bitmapDrawable = new BitmapDrawable(rawInputStream);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Logger.i("从inputStream读出来的bitmap--" + bitmap.getWidth() + "--" + bitmap.getHeight());
        Logger.i("从inputStream读出来的bitmapDrawable--" + bitmapDrawable.getIntrinsicWidth() + "--" + bitmapDrawable.getIntrinsicHeight());

        BitmapDrawable bitmapDrawable2 = new BitmapDrawable(getResources(), bitmap);
        Bitmap bitmap2 = bitmapDrawable2.getBitmap();
        Logger.i("从new BitmapDrawable(getResources(),bitmap);的bitmapDrawable--" + bitmapDrawable2.getIntrinsicWidth() + "--" + bitmapDrawable2.getIntrinsicHeight());

        Matrix matrix = new Matrix();
        matrix.postScale(4, 4);
        imageMatrix.setImageMatrix(matrix);
        imageMatrix.setScaleType(ImageView.ScaleType.MATRIX);
        imageMatrix.getLayoutParams().width = (bitmapDrawable2.getBitmap().getWidth() * 4);
        imageMatrix.getLayoutParams().height = (bitmapDrawable2.getBitmap().getHeight() * 4);

        imageMatrix.setImageDrawable(bitmapDrawable2);

        Logger.i("使用的bitmap--" + ((BitmapDrawable) imageMatrix.getDrawable()).getBitmap().getWidth() + "--" + ((BitmapDrawable) imageMatrix.getDrawable()).getBitmap().getHeight());
        Logger.i("使用的bitmapDrawable--" + ((BitmapDrawable) imageMatrix.getDrawable()).getIntrinsicWidth() + "--" + ((BitmapDrawable) imageMatrix.getDrawable()).getIntrinsicWidth());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.i("imageview--" + imageMatrix.getWidth() + "--" + imageMatrix.getHeight());
            }
        }, 1000);
    }

    public static void actionStart(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, BitmapActivity.class));
    }
}
