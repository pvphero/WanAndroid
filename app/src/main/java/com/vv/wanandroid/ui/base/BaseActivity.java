package com.vv.wanandroid.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.vv.wanandroid.R;
import com.vv.wanandroid.event.RxEvent;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ShenZhenWei on 2018/5/30.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected FrameLayout mContainerLayout;
    private ProgressDialog loadingDialog = null;
    private PublishSubject mSubject;
    private DisposableObserver mDisposableObserver;
    private RxEvent mRxEvent;

    @Override
    protected void onCreate(Bundle bundle) {
        if (bundle != null) {
            //如果系统回收Activity,但是系统却保留了Fragment,当Activity被重新初始化,此时,系统保存的Fragment的getActivity为空,
            //
            String fragmentsTag = "android:support:fragments";
            bundle.remove(fragmentsTag);
        }
        super.onCreate(bundle);
        //如果底部导航栏,将内容布局设置在导航栏上方
        setContentView(R.layout.activity_base);
        Intent intent = getIntent();
        if (intent != null) {
            getIntent(intent);
        }
        mToolbar = findViewById(R.id.toolbar);
        mContainerLayout = findViewById(R.id.frameLayout);

        //初始化ToolBar
        boolean isToolBar = initToolBar();
        if (isToolBar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
        }

        initContent(getLayoutId());


        mRxEvent = RxEvent.getInstance();
        //注册事件
        mSubject = mRxEvent.registerEvent(registerEvent());
        mDisposableObserver = new ReceivedEvent();

    }

    private class ReceivedEvent extends DisposableObserver {

        @Override
        public void onNext(Object o) {
            receiveEvent(o);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销事件
        RxEvent.getInstance().unRegisterEvent(registerEvent(), mSubject, mDisposableObserver);
    }

    /**
     * 收到Event
     *
     * @param object
     */
    protected abstract void receiveEvent(Object object);

    /**
     * 注册Event
     *
     * @return
     */
    protected abstract String registerEvent();

    /**
     * 获取LayoutId
     *
     * @return
     */
    protected abstract int getLayoutId();


    protected void onNavigationClick() {
        finish();
    }

    private void initContent(int layoutId) {
        if (layoutId != 0) {
            View contentView = LayoutInflater.from(this).inflate(layoutId, mContainerLayout, false);
            mContainerLayout.addView(contentView);
            initViews();
        }
    }

    protected abstract void initViews();

    protected abstract boolean initToolBar();

    protected abstract void getIntent(Intent intent);

    /**
     * 显示带消息的进度条
     *
     * @param title
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 创建LoadingDialog
     */
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 显示进度条
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 隐藏进度条
     */
    protected void hideLoadingDialog() {
        if (loadingDialog.isShowing() && loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
