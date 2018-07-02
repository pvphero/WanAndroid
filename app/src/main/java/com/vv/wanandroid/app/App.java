package com.vv.wanandroid.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShenZhenWei on 18/2/28.
 */

public class App extends Application {

    public static List<Activity> activities = new ArrayList<>();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();

        initAutoLayout();
    }

    private void initAutoLayout() {
    }

    public static Context getmContext() {
        return mContext;
    }

    /**
     * 退出
     */
    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}
