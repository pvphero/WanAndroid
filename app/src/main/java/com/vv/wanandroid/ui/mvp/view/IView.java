package com.vv.wanandroid.ui.mvp.view;

/**
 * Created by ShenZhenWei on 2018/5/30.
 */

@SuppressWarnings({"ALL", "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface IView {
    /**
     * 显示进度条
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 失败
     * @param msg
     */
    void showFail(String msg);

    /**
     * 错误
     */
    void showError();

    /**
     * 没有数据
     */
    void showEmpty();

    /**
     * 收藏Result
     * @param isCollect
     * @param result
     */
    void collect(boolean isCollect, String result);
}
