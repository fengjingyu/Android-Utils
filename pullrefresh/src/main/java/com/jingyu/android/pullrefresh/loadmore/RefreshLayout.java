package com.jingyu.android.pullrefresh.loadmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.headerfooter.songhang.library.SmartRecyclerAdapter;
import com.jingyu.android.pullrefresh.R;
import com.jingyu.utils.util.UtilString;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @description 封装了上下拉 ， 分页 ，无数据背景
 */
abstract public class RefreshLayout extends FrameLayout {
    int page = 1;
    boolean isBottom = false;
    /**
     * 上下拉效果的控件
     */
    protected PtrFrameLayout mPtrRefreshLayout; // 可以装任意view的下拉控件
    protected PlusRecyclerView recyclerView;// 可以监听滑动到底部的recycleView
    protected PlusRecyclerAdapter adapter;// 添加了getList等方法的adapter
    protected SmartRecyclerAdapter smartAdapter;// 可以添加headview footview,用法 new SmartRecycleAdapter(recycleAdapter)
    /**
     * 上拉加载的footview
     */
    private RelativeLayout loadingLayout;
    private TextView loadingTextview;
    private ProgressBar loadingProgressBar;
    /**
     * 一进入页面是否自动加载
     */
    private boolean autoRefresh;
    private IRefreshHandler mRefreshHandler;
    /**
     * 是否正在刷新(下拉和上拉)
     */
    public boolean isRequesting;
    /**
     * 数据为0的背景
     */
    public LinearLayout dataZeroLayout;
    /**
     * 数据为0时，背景上显示的图片
     */
    public ImageView dataZeroImageView;
    /**
     * 数据为0时，背景上显示的文字
     */
    public TextView dataZeroTextViewUp;
    /**
     * 数据为0时，背景上显示的文字
     */
    public TextView dataZeroTextViewDown;
    /**
     * 数据为0时的按钮
     */
    public Button dataZeroButton;
    /**
     * 0数据显示的文本信息
     */
    public String dataZeroTextViewUpHint = "";
    public String dataZeroTextViewDownHint = "";
    public String dataZeroButtonHint = "";
    /**
     * 0数据图片id
     */
    public int dataZeroImageId;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    public void initLayout(Context context, AttributeSet attrs) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // footview
        loadingLayout = (RelativeLayout) mInflater.inflate(R.layout.view_refresh_foot, null);
        loadingTextview = (TextView) loadingLayout.findViewById(R.id.loadingTextview);
        loadingProgressBar = (ProgressBar) loadingLayout.findViewById(R.id.loadingProgressBar);

        // 下拉刷新的view
        mInflater.inflate(R.layout.view_refresh, this, true);
        mPtrRefreshLayout = (PtrClassicFrameLayout) findViewById(R.id.ptrRefreshLayout);
        mPtrRefreshLayout.disableWhenHorizontalMove(true);
        recyclerView = (PlusRecyclerView) findViewById(R.id.recyclerView);

        dataZeroLayout = (LinearLayout) findViewById(R.id.dataZeroLayout);

        initRefreshLayoutParams();

        initHeadStyle();

        checkAutoRefresh(context, attrs);

        initZeroDataLayout();
    }

    private void initZeroDataLayout() {
        dataZeroImageView = (ImageView) dataZeroLayout.findViewById(R.id.dataZeroImageView);
        dataZeroTextViewUp = (TextView) dataZeroLayout.findViewById(R.id.dataZeroTextViewUp);
        dataZeroTextViewDown = (TextView) dataZeroLayout.findViewById(R.id.dataZeroTextViewDown);
        dataZeroButton = (Button) dataZeroLayout.findViewById(R.id.dataZeroButton);
    }

    public void setHandler(IRefreshHandler refreshHandler) {
        this.mRefreshHandler = refreshHandler;
        registerRefreshHandler();
        registerLoadHandler();
    }

    public void registerLoadHandler() {
        if (mRefreshHandler.canLoad()) {
            recyclerView.setOnBottomListener(new PlusRecyclerView.OnBottomListener() {
                @Override
                public void onBottom() {
                    loading();
                }
            });
        }
    }

    public void registerRefreshHandler() {
        // 由于该库提供的只有下拉刷新的handler，没有上拉加载的监听，所以另外得实现上拉的逻辑(在setHandler（）方法里)
        // 试过了，如果不设置ptrhandler，也可以下拉，所以得在checkCanDoRefresh里控制是否可以下拉
        mPtrRefreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 这个refreshing()只有在checkCanDoRefresh()返回为true时才会调用
                refreshing();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mRefreshHandler.canRefresh()) {
                    /**
                     * 如果是可下拉刷新的listview，这句必须调用，否则下拉与listview滑动会有冲突
                     */
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 该控件下有一个autoRefresh的属性 ，用于控制一进入页面是否自动刷新
     * <p/>
     * autoRefresh：true:一进入页面就自动刷新
     * autoRefresh：false：不会自动刷新
     */
    private void checkAutoRefresh(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout, 0, 0);
        if (arr != null) {
            autoRefresh = arr.getBoolean(R.styleable.RefreshLayout_autorefresh, false);
        }

        if (autoRefresh) {
            autoRefresh();
        }
    }

    public void autoRefresh() {
        autoRefresh(150);
    }

    public void autoRefresh(int time) {
        mPtrRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrRefreshLayout.autoRefresh();
            }
        }, time);
    }

    protected void reset() {
        page = 1;
        isBottom = false;
    }

    public void nextPage() {
        page++;
    }

    /**
     * 这里没用xml ， 如果是xml则默认的配置
     * xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
     * cube_ptr:ptr_duration_to_close="200"
     * cube_ptr:ptr_duration_to_close_header="1000"
     * cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
     * cube_ptr:ptr_resistance="1.7"
     * cube_ptr:ptr_keep_header_when_refresh="true"
     * cube_ptr:ptr_pull_to_fresh="false"
     */
    public void initRefreshLayoutParams() {
        // 默认的设置
        mPtrRefreshLayout.setResistance(1.7f);
        mPtrRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrRefreshLayout.setDurationToClose(200);
        mPtrRefreshLayout.setDurationToCloseHeader(1000);
        /*
         * true为拉的时候就刷新， false为拉了之后且释放时才刷新
         */
        mPtrRefreshLayout.setPullToRefresh(false);
        /*
         * 刷新的过程中，是否显示head
         */
        mPtrRefreshLayout.setKeepHeaderWhenRefresh(true);
        mPtrRefreshLayout.setLoadingMinTime(1000);
    }

    public abstract void initHeadStyle();

    /**
     * 下拉刷新
     */
    protected void refreshing() {
        if (!isRequesting) {
            isRequesting = true;
            reset();
            hiddenLoadingView();
            if (mRefreshHandler != null) {
                mRefreshHandler.refresh(page);
            } else {
                throw new RuntimeException(this + "回调handler为null");
            }
        }
    }

    /**
     * 上拉加载
     */
    protected void loading() {
        if (!isRequesting && !isBottom) {
            isRequesting = true;
            showLoadingView();
            if (mRefreshHandler != null) {
                mRefreshHandler.load(page);
            } else {
                throw new RuntimeException(this + "回调handler为null");
            }
        }
    }

    public void completeRefresh(int currentPage, int totalPage) {
        if (adapter.getList().isEmpty()) {
            showEmptyBackground();
            hiddenLoadingView();
        } else {
            showRecyclerView();
            if (currentPage >= totalPage) {
                isBottom = true;
                showNoNextPage();
            } else {
                hiddenLoadingView();
            }
        }

        mPtrRefreshLayout.refreshComplete();
        isRequesting = false;
    }

    protected void hiddenLoadingView() {
        loadingLayout.setVisibility(View.GONE);
    }

    protected void showLoadingView() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingTextview.setText("正在加载..");
        loadingTextview.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    protected void showNoNextPage() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingTextview.setText("已经看到最后啦!");
        loadingTextview.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    protected void showEmptyBackground() {
        if (dataZeroImageId < 0) {
            dataZeroImageView.setVisibility(View.GONE);
        } else {
            dataZeroImageView.setImageResource(dataZeroImageId);
            dataZeroImageView.setVisibility(View.VISIBLE);
        }

        if (UtilString.isBlank(dataZeroTextViewUpHint)) {
            dataZeroTextViewUp.setVisibility(View.GONE);
        } else {
            dataZeroTextViewUp.setText(dataZeroTextViewUpHint);
            dataZeroTextViewUp.setVisibility(View.VISIBLE);
        }

        if (UtilString.isBlank(dataZeroTextViewDownHint)) {
            dataZeroTextViewDown.setVisibility(View.GONE);
        } else {
            dataZeroTextViewDown.setText(dataZeroTextViewDownHint);
            dataZeroTextViewDown.setVisibility(View.VISIBLE);
        }

        if (UtilString.isBlank(dataZeroButtonHint)) {
            dataZeroButton.setVisibility(View.GONE);
        } else {
            dataZeroButton.setText(dataZeroButtonHint);
            dataZeroButton.setVisibility(View.VISIBLE);
        }

        dataZeroLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        mPtrRefreshLayout.setVisibility(View.INVISIBLE);
    }

    protected void showRecyclerView() {
        dataZeroLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        mPtrRefreshLayout.setVisibility(View.VISIBLE);
    }

    /**
     * @param append true,  原有的集合上添加
     *               false, 清空集合后添加
     */
    protected void notifyChanged(boolean append, List list) {
        if (list == null) {
            list = new ArrayList();
        }

        if (!append) {
            adapter.getList().clear();
        }

        adapter.getList().addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * @param requestPage 数据是第几页的
     * @param list        第几页的数据
     */
    public void notifyChanged(int requestPage, List list) {
        if (requestPage <= 1) {
            notifyChanged(false, list);
        } else {
            notifyChanged(true, list);
        }
    }

    public Button getDataZeroButton() {
        return dataZeroButton;
    }

    /**
     * 设置数据为零时候的背景
     */
    public void setDataZeroHint(String dataZeroTextViewUpHint, String dataZeroTextViewDownHint, int dataZeroImageId, String dataZeroButtonHint) {
        if (dataZeroTextViewUpHint == null) {
            this.dataZeroTextViewUpHint = "";
        } else {
            this.dataZeroTextViewUpHint = dataZeroTextViewUpHint;
        }

        if (dataZeroTextViewDownHint == null) {
            this.dataZeroTextViewDownHint = "";
        } else {
            this.dataZeroTextViewDownHint = dataZeroTextViewDownHint;
        }

        this.dataZeroImageId = dataZeroImageId;
        this.dataZeroButtonHint = dataZeroButtonHint;
    }

    public void setRecyclerAdapter(PlusRecyclerAdapter adapter) {
        setRecyclerAdapter(adapter, null);
    }

    public void setRecyclerAdapter(PlusRecyclerAdapter adapter, View headerView) {
        this.adapter = adapter;
        this.smartAdapter = new SmartRecyclerAdapter(adapter);
        if (headerView != null) {
            this.smartAdapter.setHeaderView(headerView);
        }
        this.smartAdapter.setFooterView(loadingLayout);
        recyclerView.setAdapter(smartAdapter);
    }

    public void setRecyclerLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }
}