package com.vv.wanandroid.ui.base;

import android.content.Intent;
import android.os.Bundle;

import com.vv.wanandroid.ui.mvp.view.IView;
import com.vv.wanandroid.utils.ToastUtils;

/**
 * Created by ShenZhenWei on 2018/5/30.
 */


public abstract class BasePresenterActivity<P extends BasePresenter<V>, V extends IView> extends BaseActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mPresenter = createPresenter();
        attachView();
    }

    @Override
    protected void onNavigationClick() {
        finish();
    }

    @Override
    protected void getIntent(Intent intent) {
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        //view置空
        detachView();
        //取消所有请求
        removeAllDisposable();
        super.onDestroy();
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void removeAllDisposable() {
        if (mPresenter != null) {
            mPresenter.removeAllDisposable();
        }
    }

    @Override
    protected boolean initToolBar() {
        return false;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void showLoading(String msg) {
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(this, msg);
    }
    @Override
    public void showError() {
    }
    @Override
    public void showEmpty() {
    }

    @Override
    public void collect(boolean isCollect, String result) {

    }

    @Override
    protected void receiveEvent(Object object) {
    }

    @Override
    protected String registerEvent() {
        return null;
    }

}
