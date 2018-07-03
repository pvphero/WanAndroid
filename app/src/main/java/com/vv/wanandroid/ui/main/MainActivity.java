package com.vv.wanandroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vv.wanandroid.R;
import com.vv.wanandroid.ui.base.BaseActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener {



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }



    @Override
    protected void initViews() {

    }



    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }
}
