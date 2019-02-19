package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/12/29.
 *
 * 列表 （已办和待办）
 *
 *
 */
class MaterialList {


    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {

        var companyNo: String? = null
        var supplierId: String? = null
        var warehouseId: String? = null
        var supplierNo: String? = null
        var creatorNo: String? = null
        var arrivalOrderNo: String? = null
        var amount: String? = null
        var state: String? = null
        var type: String? = null
        var inTime: String? = null
        var outTime: String? = null
        var sapOrderNo: String? = null
        var mInWarehouseOrderId: String? = null
        var supplierName: String? = null
        var materialName: String? = null
        var orderId: String? = null

        var factoryNo: String? = null
        var BSART: String? = null

        // 待办
        var receiveInfo: String? = null
        var backInfo: String? = null
        var orderTime: String? = null

    }
}