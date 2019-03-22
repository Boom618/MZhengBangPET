package com.ty.zbpet.bean.eventbus

/**
 * @author TY on 2019/3/22.
 * 错误消息
 */
class ErrorMessage(private val error: String?) {

    fun error(): String? {
        return error
    }
}