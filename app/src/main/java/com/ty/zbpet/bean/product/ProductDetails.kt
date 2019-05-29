package com.ty.zbpet.bean.product

import java.util.ArrayList

/**
 * @author TY on 2018/12/29.
 *
 * 详情 + 保存
 */
class ProductDetails {

    // 待办
    var list: List<ListBean>? = null

    class ListBean {

        // 待办
        var sapOrderNo: String? = null
        var goodsName: String? = null
        var goodsId: String? = null
        var goodsNo: String? = null
        var unit: String? = null
        var orderNumber: String? = null

        // 已办
        var id: String? = null
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
        var positionId: String? = null
        var startQrCode: String? = null
        var endQrCode: String? = null
        var content: String = ""
        var number: String? = null
        var sapMaterialBatchNo: String? = null
        var state: String? = null
        var orderId: String? = null
        var boxQrCode: ArrayList<String>? = null
        // 二版 采购入库类型
        var deliveryOrderList: ArrayList<OrderList>? = null

    }

    class OrderList {
        var deliveryOrderNo: String? = null
        var deliveryOrderLine: String? = null
        var sapBatchNo: String? = null
        var number: String? = null
        var unit: String? = null
    }
}