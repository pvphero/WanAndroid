package com.vv.wanandroid.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.vv.wanandroid.common.Const;
import com.vv.wanandroid.ui.adapter.BaseListAdapter;
import com.vv.wanandroid.ui.mvp.view.IListDataView;
import com.vv.wanandroid.ui.mvp.view.IView;
import com.vv.wanandroid.widget.ContainerLayout;
import com.vv.wanandroid.widget.LMRecyclerView;
import com.vv.wanandroid.widget.NoAlphaItemAnimator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ShenZhenWei on 2018/5/30.
 *
 * @param <P>
 * @param <V>
 * @param <T>
 *     @author ShenZhenWei
 */
public abstract class BaseAbListActivity<P extends BasePresenter<V>, V extends IView, T> extends BasePresenterActivity<P, V> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IListDataView<T> {

    private ContainerLayout mContainerLayout;
    private SwipeRefreshLayout mRefreshLayout;
    private LMRecyclerView mRecyclerView;
    protected BaseListAdapter mListAdapter;
    protected int page;
    protected int state;
    /**
     * 是否显示自动加载
     *
     */
    protected boolean isAutoLoadMore = true;
    protected List<T> mLisData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mRefreshLayout.setOnRefreshListener(mRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new NoAlphaItemAnimator());
        setCanLoadMore(isCanLoadMore());
    }

    /**
     * 是否开启加载更多
     *
     * @param isCanLoadMore
     */
    private void setCanLoadMore(boolean isCanLoadMore) {
        mRefreshLayout.setEnabled(isCanLoadMore);
    }

    /**
     * 是否能够加载更多
     *
     * @return
     */
    protected abstract boolean isCanLoadMore();


    /**
     *
     *
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    /**
     * 刷新列表
     *
     */
    public void refreshData() {
        state = Const.PAGE_STATE.STATE_REFRESH;
        isAutoLoadMore = true;
        page = 0;
        loadDatas();
    }


    /**
     * 加载数据
     *
     */
    protected abstract void loadDatas();


}
