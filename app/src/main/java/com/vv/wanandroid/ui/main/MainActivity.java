package com.vv.wanandroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.vv.wanandroid.R;
import com.vv.wanandroid.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.btn_main)
    Button btnMain;
    @Bind(R.id.btn_system)
    Button btnSystem;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
//    @Bind(R.id.navigation_view)
//    NavigationView mNavigationView;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
//        mNavigationView.setItemIconTintList(null);
//        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initToolBar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return true;
    }

    /**
     * 设置当前页面的Title
     */
    public void setCurrentTitle() {
        if (mCurrentPosition == 0) {
            mToolbar.setTitle(R.string.app_name);
        } else {
            mToolbar.setTitle(R.string.system);
        }
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initViews() {
        btnMain.setSelected(true);
    }

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }


    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.btn_main, R.id.btn_system})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main:
                break;
            case R.id.btn_system:
                break;
            default:
                break;
        }
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_favorite_article:
                    break;
                case R.id.menu_about:
                    break;
                case R.id.menu_exit:
                    break;
                default:
                    break;
            }
            return true;
        }
    };
}
