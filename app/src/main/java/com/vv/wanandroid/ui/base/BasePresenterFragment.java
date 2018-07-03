package com.vv.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vv.wanandroid.ui.mvp.view.IView;

/**
 * Created by ShenZhenWei on 2018/7/2.
 */
public abstract class BasePresenterFragment<P extends BasePresenter<V>, V extends IView> extends BaseFragment implements IView {
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        attachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.removeAllDisposable();
            mPresenter = null;
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P createPresenter();
}
