package com.ljj.base.util;

import android.content.Context;

/**
 * Created by 一锅子鱼 on 2018/11/15.
 */
public class DensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float density =
                context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float density =
                context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

}
