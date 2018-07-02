package com.vv.wanandroid.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.vv.wanandroid.R;

/**
 * Created by ShenZhenWei on 2018/5/31.
 */

public class ContainerLayout extends FrameLayout{

    private Context mContext;
    private LayoutInflater mInflater;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mContentView;

    public ContainerLayout(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingView=mInflater.inflate(R.layout.loading_layout,this,false);
        mErrorView=mInflater.inflate(R.layout.error_layout,this,false);
        mEmptyView=mInflater.inflate(R.layout.empty_layout,this,false);

        addView(mLoadingView);
        addView(mErrorView);
        addView(mEmptyView);

        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            int count=getChildCount();
            for (int i=0;i<count;i++){
                mContentView=getChildAt(i);
                if (mContentView instanceof RecyclerView){
                    break;
                }
            }
        }
    }

    //loading
    public void showLoading(){
        mLoadingView.setVisibility(VISIBLE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        if (mContentView!=null){
            mContentView.setVisibility(GONE);
        }
    }

    //error
    public void showError(){
        mErrorView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        if (mContentView!=null){
            mContentView.setVisibility(GONE);
        }
    }

    //empty
    public void showEmpty(){
        mEmptyView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        if (mContentView!=null){
            mContentView.setVisibility(GONE);
        }
    }

    //content
    public void showContent(){
        if (mContentView!=null){
            mContentView.setVisibility(VISIBLE);
        }
        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
    }
}
