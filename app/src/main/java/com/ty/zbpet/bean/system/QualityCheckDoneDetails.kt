package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/12.
 *
 * 质检已办详情
 */
class QualityCheckDoneDetails {

    var list: List<DataBean>? = null

    class DataBean{

        var arrivalOrderNo: String? = null
        var materialName: String? = null
        var arrivalTime: String? = null
        var materialNo: String? = null
        var unit: String? = null
        var unitName: String? = null
        var pathList: List<String>? = null
    }
}
