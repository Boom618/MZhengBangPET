package com.ty.zbpet.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.WindowManager
import com.ty.zbpet.ui.MainApp

/**
 * @author TY on 2019/1/7.
 * 系统屏幕属性
 */
object WindowUtil {

    /**
     * 获取屏幕宽度
     * 手持机尺寸    screenHeight = 800  screenWidth = 480   display = 1.5  dpi = 240
     * p20 手机尺寸  screenHeight = 2240 screenWidth = 1080  display = 3.0  dpi = 480
     */
    @JvmStatic
    fun getScreenWidth(): Int {
        val wm = MainApp.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.x
    }

    /**
     * 获取屏幕高度
     */
    @JvmStatic
    fun getScreenHeight(): Int {
        val wm = MainApp.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.y
    }

    /**
     * 获取屏幕密度
     */
    @JvmStatic
    fun getScreenDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    /**
     * 获取屏幕 Dpi
     * 低（120dpi）、中（160dpi）、高（240dpi）和超高（320dpi）
     */
    @JvmStatic
    fun getScreenDensityDpi(): Int {
        return Resources.getSystem().displayMetrics.densityDpi
    }
}