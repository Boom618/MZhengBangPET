package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/25.
 * 产品详情 bean 待办保存
 */
class ProductTodoSave {


    var warehouseId: String? = null
    var inTime: String? = null
    var sapOrderNo: String? = null
    var supplierNo: String? = null
    var creatorNo: String? = null
    var moveType: String? = null
    var orderId: String? = null
    var remark: String? = null
    var list: List<DetailsBean>? = null

    // 生产入库 仓库在外层
    var warehouseNo: String? = null
    var warehouseName: String? = null

    // 发货出库 生产、客户、成品 信息
    var productInfo: String? = null
    var customerInfo: String? = null
    var goodsInfo: String? = null

    class DetailsBean {
        /**
         * positionId : 1
         * number : 4
         * goodsId : 1786
         * sapMaterialBatchNo : sap物料批次号
         * goodsQrCodeStart : qr00001
         * goodsQrCodeEnd : qr00100
         * boxQrCode : ["box001","box002","box003","box004"]
         */

        var positionId: String? = null
        var number: String? = null
        var orderNumber: String? = null
        var goodsId: String? = null
        var goodsNo: String? = null
        var goodsName: String? = null
        var sapMaterialBatchNo: String? = null
        var startQrCode: String? = null
        var endQrCode: String? = null
        var content: String? = null
        var unit: String? = null
        var boxQrCode: List<String>? = null

        // 外采入库 仓库在 list 中
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
    }
}
