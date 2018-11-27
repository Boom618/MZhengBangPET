package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/25.
 * 原料详情 bean 待办详情
 */
class MaterialDetailsIn {


    var sapOrderNo: String? = null
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * materialName : 原辅料名称
         * materialId : 15
         * materialNo : 原辅料编号
         * unitS : 瓶
         * orderNumber : 100
         * stockNumber : 1000
         * concentration : 80
         * supplierNo :
         * supplierName :
         * ZKG :
         */

        var materialName: String? = null
        var materialId: String? = null
        var materialNo: String? = null
        var unitS: String? = null
        var orderNumber: String? = null
        var stockNumber: String? = null
        var concentration: String? = null
        var supplierNo: String? = null
        var supplierName: String? = null
        var ZKG: String? = null
    }
}
