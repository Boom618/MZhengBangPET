package com.ty.zbpet.bean.eventbus

/**
 * @author TY on 2019/3/19.
 * adapter 中 Button 点击事件
 */
class AdapterButtonClick(private val position: Int, private val type: String) {

    fun position(): Int {
        return position
    }

    fun type(): String {
        return type
    }
}