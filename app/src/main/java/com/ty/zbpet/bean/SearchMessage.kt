package com.ty.zbpet.bean

/**
 * @author TY on 2019/3/11.
 * 搜索，开始时间，结束时间
 */
class SearchMessage(private val sapOrderNo: String, private val startTime: String, private val endTime: String) {

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