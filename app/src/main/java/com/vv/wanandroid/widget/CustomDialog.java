package com.vv.wanandroid.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ShenZhenWei on 18/2/28.
 */

public class CustomDialog extends Dialog{
    /**
     * 宽度由布局文件中指定(但是最底层的宽度无效,可以多嵌套一层解决)
     * @param context
     * @param layout
     * @param style
     */
    public CustomDialog(Context context, View layout, int style) {
        super(context,style);

        setContentView(layout);

        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * 宽高有该方法的参数设置
     * @param context
     * @param width
     * @param height
     * @param layout
     * @param style
     */
    public CustomDialog( Context context,int width,int height,View layout,int style) {
        super(context,style);
        //设置内容
         setContentView(layout);
         Window window=getWindow();
         WindowManager.LayoutParams params=window.getAttributes();
         //设置宽度,高度,密度,对齐方式
        float density=getDensity(context);
        params.width=(int)(width*density);
        params.height=((int) (height*density));
        params.gravity=Gravity.CENTER;
        window.setAttributes(params);

    }

    /**
     * 获取显示密度
     * @param context
     * @return
     */
    private float getDensity(Context context) {
        Resources res=context.getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        return dm.density;
    }
}
