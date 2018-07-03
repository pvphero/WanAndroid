package com.vv.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vv.wanandroid.event.RxEvent;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * @author ShenZhenWei
 * @date 18/2/28
 */

public abstract class BaseFragment extends Fragment {
    private PublishSubject mSubject;
    private RxEvent mRxEvent;
    private DisposableObserver mDisposableObserver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            getBundle(bundle);
        }
        mRxEvent = RxEvent.getInstance();
        mDisposableObserver = new ReceiveEvent();
        mSubject = mRxEvent.registerEvent(registerEvent());
        mSubject.subscribe(mDisposableObserver);
    }

    /**
     * 注册Event
     *
     * @return
     */
    protected abstract String registerEvent();

    private class ReceiveEvent extends DisposableObserver {

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

    /**
     * 收到Event
     *
     * @param o
     */
    protected abstract void receiveEvent(Object o);

    /**
     * 获取Bundle
     *
     * @param bundle
     */
    protected abstract void getBundle(Bundle bundle);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            initViews(view);
        }
        return view;
    }

    /**
     * 初始化View
     *
     * @param view
     */
    protected abstract void initViews(View view);

    /**
     * 获取LayoutID
     *
     * @return
     */
    protected abstract int getLayoutId();
}
