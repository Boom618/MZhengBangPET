package com.ty.zbpet.bean.product

import com.ty.zbpet.bean.UserInfo

/**
 * @author TY on 2018/11/25.
 *
 * 产品详情 bean 已办保存
 */
class ProductDoneSave {


    var warehouseId: String? = null
    var outTime: String? = null
    var sapOrderNo: String? = null
    var orderId: String? = null
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

        var id: String? = null
        var inWarehouseOrderId: String? = null
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
        var positionId: String? = null
        var goodsId: String? = null
        var goodsName: String? = null
        var goodsNo: String? = null
        var unit: String? = null
        var number: String? = null
        var orderNumber: String? = null
        var productionDate: String? = null
        var endQrCode: String? = null
        var startQrCode: String? = null
        var expirationDate: String? = null
        var sapMaterialBatchNo: String? = null
        var state: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
        var orderId: String? = null
        var boxQrCode: List<String>? = null


        var warehouseList: List<UserInfo.WarehouseListBean>? = null

    }

}
