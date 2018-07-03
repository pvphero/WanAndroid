package com.vv.wanandroid.ui.presenter;

import com.vv.wanandroid.ui.mvp.view.IView;

import io.reactivex.disposables.Disposable;

/**
 * @author ShenZhenWei
 * @date 2018/7/3
 */
public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView();

    boolean isViewAttached();

    void checkAttachView();

    V getView();

    void addDisposable(Disposable disposable);

    void removeDisposable(Disposable disposable);

    void removeAllDisposable();
}
