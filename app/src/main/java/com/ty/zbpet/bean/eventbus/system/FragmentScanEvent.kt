package com.ty.zbpet.bean.eventbus.system

/**
 * @author TY on 2019/3/22.
 * Fragment  对应扫码信息 positionNo 库位码
 */
class FragmentScanEvent(private val type: String?, private val positionNo: String?) {

    fun getType(): String? {
        return type
    }

    fun getPositionNo(): String? {
        return positionNo
    }
}