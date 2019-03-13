package com.ty.zbpet.bean

/**
 * @author TY on 2019/3/11.
 * 搜索，开始时间，结束时间
 */
class SearchMessage(private val signType: String, private val sapOrderNo: String,
                    private val startTime: String, private val endTime: String) {

    fun sign():String{
        return signType
    }

    fun getSearch(): String {
        return sapOrderNo
    }

    fun leftTime(): String {
        return startTime
    }

    fun rightTime(): String {
        return endTime
    }

}