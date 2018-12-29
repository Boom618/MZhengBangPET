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
        /**
         * sapOrderNo : 201808071024
         * state : 状态
         * type : 采购类型
         * supplierName : 正邦上海宝山淞南分部供应商
         * supplierId : 12
         * supplierNo : 20180910
         * receiveInfo : 领料信息
         * productInfo : 生产信息
         * customerInfo : 客户信息
         * backInfo : 退货信息
         * orderTime : 2018-10-11 10:01:00
         */

        // 待办
        var sapOrderNo: String? = null
        var state: String? = null
        var type: String? = null
        var supplierName: String? = null
        var supplierId: String? = null
        var supplierNo: String? = null
        var receiveInfo: String? = null
        var productInfo: String? = null
        var customerInfo: String? = null
        var goodsInfo: String? = null
        var backInfo: String? = null
        var orderTime: String? = null

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