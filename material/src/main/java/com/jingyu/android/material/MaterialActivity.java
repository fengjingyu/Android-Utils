package com.jingyu.android.material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jingyu.android.middle.base.BaseActivity;
import com.jingyu.android.material.recyclerview.WaterFallAdapter;
import com.jingyu.utils.function.Logger;

public class MaterialActivity extends BaseActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton floatButton;
    RecyclerView recyclerView;
    WaterFallAdapter waterFallAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        Toolbar toolbar = getViewById(R.id.toolbar);
        toolbar.setTitle("android");
        setSupportActionBar(toolbar);
        drawerLayout = getViewById(R.id.activity_drawerlayout);
        navigationView = getViewById(R.id.navigationView);
        floatButton = getViewById(R.id.floatButton);
        recyclerView = getViewById(R.id.recyclerView);
        swipeRefreshLayout = getViewById(R.id.swipeRefreshLayout);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);// 返回键
            // supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);//更改返回键图标
        }

        navigationView.setCheckedItem(R.id.nav_item3);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_item1:
                        Logger.shortToast("1");
                        break;
                    case R.id.nav_item2:
                        Logger.shortToast("2");
                        break;
                    case R.id.nav_item3:
                        Logger.shortToast("3");
                        break;
                    case R.id.nav_item4:
                        Logger.shortToast("4");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logger.shortToast("floatActionButton");
                Snackbar.make(v, "message", Snackbar.LENGTH_LONG).setAction("click", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logger.shortToast("floatActionButton");
                    }
                }).show();
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(waterFallAdapter = new WaterFallAdapter(null));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        waterFallAdapter.setOnClickAction(new WaterFallAdapter.OnClickAction() {
            @Override
            public void onClickAction(View view) {
                int position = (int) view.getTag();
                //Logger.shortToast("onClickAction--" + position + "--" + waterFallAdapter.getList().get(position));
                CollapsingToolbarLayoutActivity.actionStart(getActivity());
            }

            @Override
            public void onLongClickAction(View view) {
                int position = (int) view.getTag();
                //Logger.shortToast("onLongClickAction--" + position + "--" + waterFallAdapter.getList().get(position));
                CollapsingToolbarLayoutActivity.actionStart(getActivity());

            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                waterFallAdapter.getList().clear();
                                waterFallAdapter.getList().addAll(waterFallAdapter.getData());
                                waterFallAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 返回键的默认id
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_item1:
                Logger.shortToast("1");
                break;
            case R.id.toolbar_item2:
                Logger.shortToast("2");
                break;
            case R.id.toolbar_item3:
                Logger.shortToast("3");
                break;
            case R.id.toolbar_item4:
                Logger.shortToast("4");
                break;
            default:
                break;
        }
        return true;
    }

    public static void actionStart(Activity activity) {
        activity.startActivity(new Intent(activity, MaterialActivity.class));
    }
}
