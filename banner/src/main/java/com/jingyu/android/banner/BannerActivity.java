package com.jingyu.android.banner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jingyu.android.middle.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class BannerActivity extends BaseActivity {
    private ConvenientBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        banner = getViewById(R.id.banner);
        banner.setManualPageable(true);
        banner.setCanLoop(true);

        List<String> imgs = new ArrayList<String>();
        imgs.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        imgs.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");

        banner.setPages(new CBViewHolderCreator<ConvenientBannerHolder>() {
            @Override
            public ConvenientBannerHolder createHolder() {
                return new ConvenientBannerHolder(R.mipmap.ic_launcher);
            }
        }, imgs)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, BannerActivity.class));
    }
}
