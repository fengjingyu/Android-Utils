package com.jingyu.android.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.jingyu.android.middle.AppImg;

/**
 * Created by xtyx_jy on 2017/4/1.
 */

public class ConvenientBannerHolder implements Holder<String> {
    private ImageView imageView;
    private int imgId;

    public ConvenientBannerHolder(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String url) {
        AppImg.display(url, imageView, imgId, imgId);
    }
}
