package com.vv.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vv.wanandroid.R;
import com.vv.wanandroid.common.Const;
import com.vv.wanandroid.ui.adapter.BaseListAdapter;
import com.vv.wanandroid.ui.mvp.view.IListDataView;
import com.vv.wanandroid.ui.mvp.view.IView;
import com.vv.wanandroid.utils.LogUtils;
import com.vv.wanandroid.utils.ToastUtils;
import com.vv.wanandroid.widget.ContainerLayout;
import com.vv.wanandroid.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShenZhenWei on 2018/7/2.
 */
public abstract class BaseAbListFragment<P extends BasePresenter<V>, V extends IView, T> extends BasePresenterFragment<P, V> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IListDataView<T> {

    private static final String TAG = "BaseAbListFragment";
    private SwipeRefreshLayout mRefreshlayout;
    private ContainerLayout mContainerLayout;
    private LMRecyclerView mRecyclerView;
    private List<T> mListData = new ArrayList<>();
    private BaseListAdapter mListAdapter;
    private int state;
    private boolean isAutoLoadMore;
    private int page;


    @Override
    protected void initViews(View view) {
        mRefreshlayout = view.findViewById(R.id.refresh_layout);
        mContainerLayout = view.findViewById(R.id.container_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public List<T> getData() {
        return mListData;
    }

    @Override
    public void showContent() {
        mContainerLayout.showContent();
        mListAdapter.notifyAllData(mListData, mRecyclerView);
    }

    @Override
    public void collect(boolean isCollect, String result) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_rectcler_list;
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefreshlayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setCanLoadMore(isCanLoadMore());
        mRecyclerView.addFooterAutoLoadMoreListener(this);
    }

    protected abstract boolean isCanLoadMore();


    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    private void refreshData() {
        state = Const.PAGE_STATE.STATE_REFRESH;
        isAutoLoadMore = true;
        page = 0;
        loadDatas();
        LogUtils.iTag(TAG, "下拉刷新...");
    }

    @Override
    public void loadMore() {
        if (!isAutoLoadMore) {
            return;
        }
        state = Const.PAGE_STATE.SATE_LOAD_MORE;
        loadDatas();
        LogUtils.iTag(TAG, "加载更多...");
    }

    @Override
    public void reloadMore() {
        isAutoLoadMore = true;
        loadMore();
        LogUtils.iTag(TAG, "重新加载更多...");
    }

    @Override
    public void clearListData() {
        mListData.clear();
        LogUtils.iTag(TAG, mListData.size() + "条数据被清空");
    }

    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = true;
    }

    @Override
    public void showError() {
        isAutoLoadMore = false;
        if (state == Const.PAGE_STATE.SATE_LOAD_MORE) {
            mRecyclerView.showLoadMoreError();
            LogUtils.iTag(TAG, "加载数据出错,显示重新加载..");
        } else {
            mContainerLayout.showError();
            LogUtils.iTag(TAG, "请求失败,显示没有数据");
        }
    }

    @Override
    public void showEmpty() {
        mContainerLayout.showEmpty();
        LogUtils.iTag(TAG, "无数据");
    }

    @Override
    public int getPage() {
        LogUtils.iTag(TAG, "页数为:" + page);
        return page;
    }

    @Override
    public void showLoading(String msg) {
        if (state == Const.PAGE_STATE.STATE_REFRESH) {
            setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        setRefreshing(false);
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(getActivity(), msg);
        LogUtils.iTag(TAG, "失败");
    }

    private void setRefreshing(final boolean isRefreshing) {
        mRefreshlayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshlayout.setRefreshing(isRefreshing);
            }
        }, 100);
    }

    @Override
    protected void receiveEvent(Object o) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    protected abstract void loadDatas();

    protected abstract View initHeardView();

    protected abstract BaseListAdapter getListAdapter();
}
