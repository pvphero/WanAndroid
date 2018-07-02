package com.vv.wanandroid.ui.base;

/**
 * Created by ShenZhenWei on 18/3/2.
 */

public class Test {
    private static volatile Test sington;

    public static Test getInstance() {
        if (sington == null) {
            synchronized (Test.class) {
                if (sington == null) {
                    sington = new Test();
                }
            }
        }
        return sington;
    }

}
