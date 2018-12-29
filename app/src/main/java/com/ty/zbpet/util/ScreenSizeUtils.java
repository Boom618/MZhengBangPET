package com.ty.zbpet.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.MutableChar;
import android.view.WindowManager;

/**
 * create by Weavey
 * on date 2016-01-06
 * @author TY
 */

public class ScreenSizeUtils {

    private WindowManager manager;
    private DisplayMetrics dm;
    private static ScreenSizeUtils instance = null;
    private int screenWidth, screenHeigth;

    public static ScreenSizeUtils getInstance(Context mContext) {

        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {

                if (instance == null) {
                    instance = new ScreenSizeUtils(mContext);
                }

            }
        }
        return instance;
    }

    private ScreenSizeUtils(Context mContext) {

        manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        // 获取屏幕分辨率宽度
        screenWidth = dm.widthPixels;
        screenHeigth = dm.heightPixels;

    }

    //获取屏幕宽度
    public int getScreenWidth() {

        return screenWidth;
    }

    //获取屏幕高度
    public int getScreenHeight() {

        return screenHeigth;
    }

}