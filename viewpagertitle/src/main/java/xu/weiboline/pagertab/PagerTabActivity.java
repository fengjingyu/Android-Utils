package xu.weiboline.pagertab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.jingyu.android.init.fragment.tab.TabFragmentOne;
import com.jingyu.android.init.fragment.tab.TabFragmentTwo;
import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.utils.adapter.PlusAdapterPagerFragment;

import java.util.ArrayList;
import java.util.List;

import xu.weiboline.R;

public class PagerTabActivity extends BaseActivity {

    private PagerSlidingTab tab;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_tab);
        initView();
    }

    public void initView() {
        tab = getViewById(R.id.tab);
        viewPager = getViewById(R.id.viewPager);

        TabFragmentOne fragment = new TabFragmentOne();
        TabFragmentTwo fragment2 = new TabFragmentTwo();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragment);
        fragmentList.add(fragment2);

        List<String> titles = new ArrayList<>();
        titles.add("tab1");
        titles.add("tab2");

        viewPager.setAdapter(new PlusAdapterPagerFragment(getSupportFragmentManager(), fragmentList, titles));
        tab.setViewPager(viewPager);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        // 设置Tab是自动填充满屏幕的 ，如果是铺满全屏传true
        tab.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tab.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tab.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics));
        // 设置Tab底部线的padding
        tab.setUnderlinePadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics));
        // 设置Tab Indicator的高度
        tab.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, displayMetrics));
        // 设置Tab标题文字的大小
        tab.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, displayMetrics));
        // 设置Tab Indicator的颜色
        tab.setIndicatorColor(Color.parseColor("#00D89A"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tab.setSelectedTextColor(Color.parseColor("#00D89A"));
        // 取消点击Tab时的背景色
        tab.setTabBackground(0);

        tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
