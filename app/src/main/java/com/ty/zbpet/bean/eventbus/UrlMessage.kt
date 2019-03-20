package com.ty.zbpet.bean.eventbus

/**
 * @author TY on 2019/3/20.
 * 解析 URL 地址
 */
class UrlMessage(private val position: Int, private val qrCode: String) {

    fun getPosition(): Int {
        return position
    }

    fun qrCode(): String {
        return qrCode
    }
}