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
     * 数据为0的背景
     */
    public LinearLayout dataZeroLayout;
    /**
     * 当前页
     */
    public int currentPage = 1;
    /**
     * 总页数
     */
    public int totalPage = -1;
    /**
     * 是否正在刷新(下拉和上拉)
     */
    public boolean isRequesting;
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
    /**
     * 记录上一次请求是否成功
     */
    boolean lastRequestResult;

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
                    checkAndLoad();
                }
            });
        }
    }

    public void checkAndLoad() {
        try {
            if (totalPage > 1 && !isRequesting) { // 必须有多页 && 必须是之前的请求已返回后
                if (currentPage > 1 || currentPage == 1 && lastRequestResult) { // 不是第一页 或者 是第一页但必须第一页刷新成功后
                    if (currentPage < totalPage || (currentPage == totalPage && !lastRequestResult)) {//不是最后一页 或者 是最后一页但是最后一页访问失败了
                        loading();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            hiddenLoadingView();
            currentPage = 1;
            if (mRefreshHandler != null) {
                mRefreshHandler.refresh(currentPage);
            } else {
                throw new RuntimeException(this + "回调handler为null");
            }
        }
    }

    /**
     * 上拉加载
     */
    protected void loading() {
        if (!isRequesting) {
            isRequesting = true;
            showLoadingView();
            if (lastRequestResult) {
                currentPage = currentPage + 1;
            }
            if (mRefreshHandler != null) {
                mRefreshHandler.load(currentPage);
            } else {
                throw new RuntimeException(this + "回调handler为null");
            }
        }
    }

    public void showLoadingView() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingTextview.setText("正在加载..");
        loadingTextview.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    public void hiddenLoadingView() {
        loadingLayout.setVisibility(View.GONE);
    }

    public void showNoNextPage() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingTextview.setText("已经看到最后啦!");
        loadingTextview.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    /**
     * 刷新完成,需要外部调用
     */
    public void completeRefresh(boolean requestSuccess) {
        this.lastRequestResult = requestSuccess;

        if (requestSuccess) {
            if (adapter.getList().isEmpty()) {
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

                hiddenLoadingView();
            } else {
                dataZeroLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                mPtrRefreshLayout.setVisibility(View.VISIBLE);
                if (currentPage >= totalPage) {
                    if (adapter.getList().size() >= 3) {
                        showNoNextPage();
                    } else {
                        //未满一页
                        hiddenLoadingView();
                    }
                } else {
                    // 不是最后一页
                    hiddenLoadingView();
                }
            }
        } else {
            // 比如刚打开页面访问失败,这时可以刷新,虽然数据是0,但不显示0背景页面
            // 比如第一页加载成功,第二页失败了,这时不显示0背景页面
            hiddenLoadingView();
        }

        mPtrRefreshLayout.refreshComplete();
        isRequesting = false;
    }

    public void setTotalPage(int totalPage) {
        if (totalPage < 1) {
            totalPage = 1;
        }
        this.totalPage = totalPage;
    }

    /**
     * 如果是第一页则需要清空集合
     */
    protected void clearWhenPageOne() {
        if (currentPage == 1) {
            adapter.getList().clear();
        }
    }

    protected void updateList(boolean append, List list) {
        if (list == null) {
            list = new ArrayList();
        }

        if (!append) {
            adapter.getList().clear();
        } else {
            clearWhenPageOne();
        }

        adapter.getList().addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void updateAppend(List list) {
        updateList(true, list);
    }

    public void updateClearAndAdd(List list) {
        updateList(false, list);
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
