package com.vv.wanandroid.ui.base;

import com.vv.wanandroid.ui.mvp.view.IView;
import com.vv.wanandroid.ui.presenter.IPresenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ShenZhenWei on 18/2/27.
 */

public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    protected Reference<V> mViewRef;

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    @Override
    public V getView() {
        checkAttachView();
        return mViewRef.get();
    }

    @Override
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    public void checkAttachView() {
        if (mViewRef == null || mViewRef.get() == null) {
            throw new RuntimeException("You have no binding this view");
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void removeDisposable(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.remove(disposable);
        }
    }

    @Override
    public void removeAllDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
