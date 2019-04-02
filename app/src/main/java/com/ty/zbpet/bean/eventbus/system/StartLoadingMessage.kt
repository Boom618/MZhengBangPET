package com.ty.zbpet.bean.eventbus.system

/**
 * @author TY on 2019/3/22.
 * 开始加载
 */
class StartLoadingMessage(private val loadMsg: String) {

    fun message(): String {
        return loadMsg
    }
}