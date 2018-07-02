package com.vv.wanandroid.ui.mvp.view;

import java.util.List;

/**
 * Created by ShenZhenWei on 2018/5/31.
 */

public interface IListDataView<T> extends IView {

    /**
     * 页码
     * @return
     */
    int getPage();

    /**
     * 填充数据
     * @param data
     */
    void setData(List<T> data);

    /**
     * 获取数据
     * @return
     */
    List<T> getData();

    /**
     * 显示内容
     */
    void showContent();

    /**
     * 自动加载
     */
    void autoLoadMore();

    /**
     * 请空所有数据
     */
    void clearListData();

    /**
     *  没有更多数据
     */
    void showNoMore();

    /**
     * 文章ID
     * @return
     */
    int getArticleId();
}
