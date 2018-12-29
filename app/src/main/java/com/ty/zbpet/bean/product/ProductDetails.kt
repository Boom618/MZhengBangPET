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
        var unitS: String? = null
        var orderNumber: String? = null
        var warehouseList: List<WarehouseListBean>? = null

        // 已办
        var id: String? = null
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
        var positionId: String? = null
        var startQrCode: String? = null
        var endQrCode: String? = null
        var content: String? = null
        var number: String? = null
        var sapMaterialBatchNo: String? = null
        var state: String? = null
        var orderId: String? = null
        var boxQrCode: ArrayList<String>? = null

        // 外采入库 每个列表都有仓库选项
        class WarehouseListBean {

            var warehouseId: String? = null
            var warehouseNo: String? = null
            var warehouseName: String? = null
        }
    }
}