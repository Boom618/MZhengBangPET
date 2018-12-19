package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/12.
 *
 * 质检待办详情
 */
class QualityCheckTodoDetails{

    /**
     * arrivalOrderNo : 201808071024
     * materialName : 原料1
     * materialNo : 001
     */


    var list: List<DataBean>? = null

    class DataBean{

        var arrivalOrderNo: String? = null
        var materialName: String? = null
        var arrivalTime: String? = null
        var materialNo: String? = null
        var unit: String? = null
        var unitName: String? = null
    }
}
