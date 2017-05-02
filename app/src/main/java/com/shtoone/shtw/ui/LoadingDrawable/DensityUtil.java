package com.shtoone.shtw.ui.LoadingDrawable;

import android.content.Context;

public class DensityUtil {

    public static float dip2px(Context context, float dpValue) {//把dp单位的数据转换成px单位
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }  
}