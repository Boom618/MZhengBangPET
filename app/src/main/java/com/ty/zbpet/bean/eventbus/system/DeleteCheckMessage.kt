package com.ty.zbpet.bean.eventbus.system

/**
 * @author TY on 2019/3/22.
 * 删除单据消息
 */
class DeleteCheckMessage(private val id: String?, private val sapCheckNo: String?) {

    fun getId(): String? {
        return id
    }

    fun sapCheckNo(): String? {
        return sapCheckNo
    }
}