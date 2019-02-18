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
        /**
         * sapOrderNo : SAP00011
         * mOutWarehouseOrderNo : null
         * state : 1
         * type : 21
         * companyNo : C000014
         * warehouseId : 10
         * recallOrderId : null
         * userId : 23
         * creator : test
         * outTime : 2018-09-18 10:56:00
         * createdAt : 2018-09-18T02:56:01.000Z
         * updatedAt : 2018-09-18T02:56:01.000Z
         * materialName : 黄豆——批次1000,
         * mOutWarehouseOrderId : 36
         * supplierName :
         */


        var mInWarehouseOrderNo: String? = null
        var mOutWarehouseOrderId: String? = null
        var companyNo: String? = null
        var supplierId: String? = null
        var warehouseId: String? = null
        var supplierNo: String? = null
        var arrivalOrderNo: String? = null
        var amount: String? = null
        var state: String? = null
        var type: String? = null
        var inTime: String? = null
        var outTime: String? = null
        var userId: String? = null
        var creator: String? = null
        var recallOrderId: String? = null
        var sapOrderNo: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
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