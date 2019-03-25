package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/12.
 *
 * 质检待办
 */
class QualityCheckTodoList {

    var count: Int = 0
    var list: List<DataBean>? = null

    class DataBean {
        /**
         * arrivalOrderNo : 201808071024
         * state : 已办
         * arrivalTime : 2018-08-07
         * type : 外采
         */

        var arrivalOrderNo: String? = null
        var sapOrderNo: String? = null
        var state: String? = null
        var arrivalTime: String? = null
        var type: String? = null
    }
}
