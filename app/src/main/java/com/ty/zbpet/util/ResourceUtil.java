package com.ty.zbpet.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.ty.zbpet.ui.MainApp;


public class ResourceUtil {

    /**
     * 获取Application Context
     *
     * @return
     */
    public static Context getContext() {
        return MainApp.getContext();
    }

    /**
     * 获取 Resources
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics()));

    }

    /**
     * 获取String
     * @param strId
     * @return
     */
    public static String getString(int strId) {
        return getResources().getString(strId);
    }

    /**
     * 获取Color
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }
}
