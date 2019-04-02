package com.ty.zbpet.bean.eventbus.system

/**
 * @author TY on 2019/3/22.
 * 结束加载
 */
class EndLoadingMessage(private val loadMsg: String?) {

    fun message(): String? {
        return loadMsg
    }
}