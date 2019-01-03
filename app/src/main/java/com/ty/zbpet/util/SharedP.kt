package com.ty.zbpet.util

import android.content.Context

/**
 * @author TY on 2019/1/3.
 */
object SharedP {


    /**
     * 存 点击库位码 是否有焦点和位置
     */
    fun putHasFocusAndPosition(context: Context, hasFocus: Boolean, position: Int) {
        context.getSharedPreferences("shared", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("hasFocus", hasFocus)
                .putInt("position", position)
                .apply()
    }

    fun getFocus(context: Context): Boolean {
        return context.getSharedPreferences("shared", Context.MODE_PRIVATE)
                .getBoolean("hasFocus", false)
    }

    fun getPosition(context: Context): Int {
        return context.getSharedPreferences("shared", Context.MODE_PRIVATE)
                .getInt("position", -1)
    }

    fun clearFocusAndPosition(context: Context) {
        context.getSharedPreferences("shared", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("hasFocus", false)
                .putInt("position", -1)
                .apply()
    }
}