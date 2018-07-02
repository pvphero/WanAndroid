package com.vv.wanandroid.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.vv.wanandroid.common.ListDataHolder;
import com.vv.wanandroid.widget.LMRecyclerView;

import java.util.List;

/**
 * Created by ShenZhenWei on 2018/5/31.
 */

@SuppressWarnings({"ALL", "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<ListDataHolder>{

    private List<T> mList;

    //刷新所有数据
    public void notifyAllData(List<T> mList, LMRecyclerView recyclerView){
        this.mList=mList;
        recyclerView.notifyDataSetChanged();
    }

    //刷新单条数据
    public void notifyItemDataChanged(int position ,LMRecyclerView recyclerView){
        recyclerView.notifyItemChanged(position);
    }

    //移除单条数据
    public void notifyItemDataRemove(int position,LMRecyclerView recyclerView){
        recyclerView.notifyItemRemoved(position);
    }

    @Override
    public ListDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ListDataHolder.createViewHolder(parent,getLayoutId(viewType));
    }

    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    protected abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(ListDataHolder holder, int position) {
        //初始化View
        T bean=mList.get(position);
        //绑定数据
        bindData(holder,bean,holder.getItemViewType(),position);
    }

    @Override
    public int getItemCount() {
        return mList!=null ? mList.size(): 0;
    }

    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    protected abstract void bindData(ListDataHolder holder, T bean, int itemViewType, int position);
}
