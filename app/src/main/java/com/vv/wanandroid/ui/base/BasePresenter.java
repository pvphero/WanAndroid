package com.vv.wanandroid.ui.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by ShenZhenWei on 18/2/27.
 */

public abstract class BasePresenter<V> {
    protected Reference<V> mViewRef;

    public void attachView(V view){
        mViewRef=new WeakReference<V>(view);
    }

    protected V getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef!=null && mViewRef.get()!=null;
    }

    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
    }
}
