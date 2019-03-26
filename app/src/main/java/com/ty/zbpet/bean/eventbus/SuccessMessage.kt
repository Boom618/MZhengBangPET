package com.ty.zbpet.bean.eventbus

/**
 * @author TY on 2019/3/22.
 * 成功
 */
class SuccessMessage(private val success: String?) {

    fun success(): String? {
        return success
    }
}