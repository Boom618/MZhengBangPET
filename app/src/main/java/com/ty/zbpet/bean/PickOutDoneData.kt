package com.ty.zbpet.bean

/**
 * @author TY on 2018/11/21.
 * 领料出库 已办列表
 */
class PickOutDoneData {


    var list: List<ListBean>? = null

    class ListBean {
        /**
         * sapOrderNo : 201808071024
         * state : 状态
         * type : 采购类型
         * supplierName : 正邦上海宝山淞南分部供应商
         * supplierId : 12
         * orderId : 55
         * receiveInfo": "领料信息"
         * orderTime : 2018-10-11 10:01:00
         */

        var sapOrderNo: String? = null
        var state: String? = null
        var type: String? = null
        var supplierName: String? = null
        var supplierId: String? = null
        var orderId: String? = null
        var receiveInfo: String? = null
        var orderTime: String? = null
    }
}
