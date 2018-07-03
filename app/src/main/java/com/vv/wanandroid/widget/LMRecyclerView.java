package com.vv.wanandroid.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vv.wanandroid.R;
import com.vv.wanandroid.common.ListDataHolder;

/**
 * Created by ShenZhenWei on 2018/5/28.
 */

@SuppressWarnings("ALL")
public class LMRecyclerView extends RecyclerView {
    private OnFooterAutoLoadMoreListener listener;
    private boolean isCanLoadMore;//是否允许加载更多
    private boolean isReClickLoadMore;//是否可以点击重新加载
    private View mHeaderView;//头部
    private static final int VIEW_TYPE_NOMAL=0;
    private static final int VIEW_TYPE_HEADER=100;
    private static final int VIEW_TYPE_FOOTER=200;

    private int footerResId;

    private BaseAdapter mBaseAdapter;

    public LMRecyclerView(Context context) {
        this(context,null);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //添加监听
        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 是否允许加载更多
     * @param isCanLoadMore
     */
    public void setCanLoadMore(boolean isCanLoadMore){
        this.isCanLoadMore=isCanLoadMore;
    }

    /**
     * 添加heardView
     * @param heard
     */
    public void addHeaderView(View heard){
        this.mHeaderView=heard;
    }

    /**
     * 移除heardView
     */
    public void removeHeaderView(){
        this.mHeaderView=null;
    }

    /**
     * 显示底部加载状态
     * @param footerResId
     */
    public void showFooterStatus(int footerResId){
        this.footerResId=footerResId;
    }



    private OnScrollListener mOnScrollListener=new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LayoutManager layoutManager=recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager){
                LinearLayoutManager linearLayout= (LinearLayoutManager) layoutManager;
                int mLastChildPostion =linearLayout.findLastVisibleItemPosition();
                int itemTotalCount =linearLayout.getItemCount();
                View lastChildView=linearLayout.getChildAt(linearLayout.getChildCount()-1);
                int lastChildBottomn=lastChildView.getBottom();
                int recyclerBootom=getBottom();
                if (mLastChildPostion ==itemTotalCount-1 && lastChildBottomn==recyclerBootom){
                    if (isCanLoadMore && listener!=null){
                        listener.loadMore();                    }
                }
            }
        }
    };


    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter!=null){
            mBaseAdapter=new BaseAdapter(adapter);
        }
        super.swapAdapter(mBaseAdapter,true);
    }

    private class BaseAdapter extends Adapter<ListDataHolder>{

        private Adapter mAdapter;

        public BaseAdapter(Adapter adapter){
            this.mAdapter=adapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView!=null && position==0){
                return VIEW_TYPE_HEADER;
            }
            if (isCanLoadMore && position==getItemCount()-1){
                return VIEW_TYPE_FOOTER;
            }
            return VIEW_TYPE_NOMAL;
        }

        @Override
        public ListDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_HEADER){
                return ListDataHolder.createViewHolder(mHeaderView);
            }

            if (viewType== VIEW_TYPE_FOOTER){
                return ListDataHolder.createViewHolder(parent, R.layout.item_root_footer);
            }

            return (ListDataHolder) mAdapter.onCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(ListDataHolder holder, int position) {
            int viewType=getItemViewType(position);
            if (viewType==VIEW_TYPE_NOMAL){
                if (mHeaderView!=null){
                    position--;
                }
                mAdapter.onBindViewHolder(holder,position);
            }else if (viewType==VIEW_TYPE_FOOTER){
                showFooterView(holder);
            }

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private void showFooterView(ListDataHolder holder) {
        FrameLayout rootView=holder.getView(R.id.root_foot);
        rootView.removeAllViews();
        if (footerResId!=0){
            View footerView = LayoutInflater.from(getContext()).inflate(footerResId,rootView,false);
            rootView.addView(footerView);

            //只有加载更多失败,才能点击重新加载数据
            rootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isReClickLoadMore) {
                        return;
                    }
                    if (listener!=null){
                        showLoadMore();
                        notifyDataSetChanged();
                        listener.loadMore();
                    }
                }
            });

        }
    }

    public void notifyDataSetChanged() {
        getAdapter().notifyDataSetChanged();
    }

    public void notifyItemChanged(int position){
        getAdapter().notifyItemChanged(position);
    }

    public void notifyItemRemoved(int postion){
        getAdapter().notifyItemRemoved(postion);
        getAdapter().notifyItemRangeRemoved(postion,getAdapter().getItemCount());
    }

    /**
     * 显示底部加载更多
     */
    public void showLoadMore() {
        showFooterStatus(R.layout.item_footer_loading_more);
        setIsReClickLoadMore(false);
    }

    /**
     * 加载更多,显示没有更多数据了
     */
    public void showNoMoreData(){
        showFooterStatus(R.layout.item_footer_nomore);
        setIsReClickLoadMore(false);
    }

    /**
     * 加载更多,加载失败
     */
    public void showLoadMoreError(){
        showFooterStatus(R.layout.item_footer_load_error);
        setIsReClickLoadMore(true);
    }

    /**
     * 底部是否可以重新加载更多
     * @param isReClickLoadMore
     */
    public void setIsReClickLoadMore(boolean isReClickLoadMore) {
        this.isReClickLoadMore=isReClickLoadMore;
    }

    public void addFooterAutoLoadMoreListener(OnFooterAutoLoadMoreListener listener){
        this.listener=listener;
    }


    public interface OnFooterAutoLoadMoreListener {
        //自动加载更多
        void loadMore();
        //加载出错,点击重新加载
        void reloadMore();
    }
}
