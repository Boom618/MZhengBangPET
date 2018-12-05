package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/25.
 * 产品详情 bean 待办保存
 */
class ProductTodoSave {


    /**
     * warehouseId : 3
     * inStoreDate : 2018-09-06
     * sapPlantNo : SAP00009
     * orderId : 666
     * productionBatchNo : P00001
     * remark : 1
     * details : [{"positionId":"1","number":"4","goodsId":"1786","sapMaterialBatchNo":"sap物料批次号","goodsQrCodeStart":"qr00001","goodsQrCodeEnd":"qr00100","boxQrCode":["box001","box002","box003","box004"]}]
     */

    var warehouseId: String? = null
    var inTime: String? = null
    var sapOrderNo: String? = null
    var orderId: String? = null
    var productionBatchNo: String? = null
    var remark: String? = null
    var details: List<DetailsBean>? = null

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
        var sapMaterialBatchNo: String? = null
        var goodsQrCodeStart: String? = null
        var goodsQrCodeEnd: String? = null
        var boxQrCode: List<String>? = null

        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
    }
}
