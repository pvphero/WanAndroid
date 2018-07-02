package com.vv.wanandroid.ui.base;

import android.os.Bundle;

import com.vv.wanandroid.ui.mvp.view.IView;

/**
 * Created by ShenZhenWei on 2018/5/30.
 */

@SuppressWarnings({"ALL", "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public abstract class BasePresenterActivity<P extends BasePresenter<V>,V extends IView > extends BaseActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        attachView();
    }



    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    protected abstract P createPresenter();

    private void attachView(){
        if (mPresenter!=null){
            mPresenter.attachView((V) this);
        }
    }

    private void detachView(){
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

}
