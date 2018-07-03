package com.vv.wanandroid.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vv.wanandroid.R;
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
 * @author ShenZhenWei
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
        mRecyclerView.addFooterAutoLoadMoreListener(this);
        mListAdapter = getListAdapter();
        if (mListAdapter != null) {
            mRecyclerView.addHeaderView(initHeaderView());
            mRecyclerView.setAdapter(mListAdapter);
            loadDatas();
        }
    }

    @Override
    protected void initViews() {
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mContainerLayout = findViewById(R.id.container_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_rectcler_list;
    }

    @Override
    public List<T> getData() {
        return mLisData;
    }

    @Override
    public void showContent() {
        mContainerLayout.showContent();
        mListAdapter.notifyAllData(mLisData, mRecyclerView);
    }

    /**
     * 下拉刷线监听
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    /**
     * 刷新列表
     */
    public void refreshData() {
        state = Const.PageState.STATE_REFRESH;
        isAutoLoadMore = true;
        page = 0;
        loadDatas();
    }

    @Override
    public void loadMore() {
        if (!isAutoLoadMore) {
            return;
        }
        state = Const.PageState.SATE_LOAD_MORE;
        loadDatas();
    }

    @Override
    public void reloadMore() {
        isAutoLoadMore = true;
        loadMore();
    }

    @Override
    public void clearListData() {
        mLisData.clear();
    }

    @Override
    public void autoLoadMore() {
        mRecyclerView.showLoadMore();
        page++;
        isAutoLoadMore = true;
    }

    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = false;
    }

    @Override
    public void showError() {
        isAutoLoadMore = false;
        if (state == Const.PageState.SATE_LOAD_MORE) {
            mRecyclerView.showLoadMoreError();
            mListAdapter.notifyAllData(mLisData, mRecyclerView);
        } else {
            mContainerLayout.showError();
        }
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void showLoading(String msg) {
        if (state == Const.PageState.STATE_REFRESH) {
            setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        setRefreshing(false);
    }

    private void setRefreshing(final boolean isRefrshing) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(isRefrshing);
            }
        }, 100);
    }

    /**
     * 是否开启加载更多
     *
     * @param isCanLoadMore
     */
    private void setCanLoadMore(boolean isCanLoadMore) {
        mRefreshLayout.setEnabled(isCanLoadMore);
    }

    public void setRefreshEnable(boolean isEnableRefresh) {
        mRefreshLayout.setEnabled(isEnableRefresh);
    }

    @Override
    public void showEmpty() {
        mContainerLayout.showEmpty();
    }


    /**
     * 是否能够加载更多
     *
     * @return
     */
    protected abstract boolean isCanLoadMore();


    /**
     * 加载数据
     */
    protected abstract void loadDatas();

    /**
     * 获取Adapter
     *
     * @return
     */
    protected abstract BaseListAdapter getListAdapter();

    /**
     * 初始化HeaderView
     *
     * @return
     */
    protected abstract View initHeaderView();
}
