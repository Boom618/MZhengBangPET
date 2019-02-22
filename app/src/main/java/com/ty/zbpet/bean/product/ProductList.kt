package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/12/29.
 *
 * 产品列表
 */
class ProductList {

    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {

        // 待办
        var sapOrderNo: String? = null
        var sapFirmNo: String? = null
        var supplierNo: String? = null
        var supplierName: String? = null
        var creatorNo: String? = null
        var orderTime: String? = null
        var content: String? = null
        var state: String? = null

        var supplierId: String? = null
        var type: String? = null
        var backInfo: String? = null
        var receiveInfo: String? = null
        var productInfo: String? = null
        var customerInfo: String? = null
        var goodsInfo: String? = null

        // 已办
        var id: String? = null
        var inWarhouseOrderNo: String? = null
        var companyNo: String? = null
        var inTime: String? = null
        var outTime: String? = null
        var productionBatchNo: String? = null
        var recallOrderId: String? = null
        var userId: String? = null
        var creator: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
    }
}