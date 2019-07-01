package com.ty.zbpet.bean.eventbus

import android.widget.TextView

/**
 * @author TY
 * @Date: 2019/7/1 16:21
 * @Description: 选择批次号
 */
class SelectBatch(private val position: Int, private val view: TextView) {

    fun getPosition(): Int {
        return position
    }

    fun getTextView():TextView{
        return view
    }

}
