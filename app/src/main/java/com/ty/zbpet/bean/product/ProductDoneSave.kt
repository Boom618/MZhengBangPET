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
    var inTime: String? = null
    var sapOrderNo: String? = null
    var orderId: String? = null
    var moveType: String? = null
    var remark: String? = null
    var list: List<DetailsBean>? = null

    class DetailsBean {

        var id: String? = null
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
        var positionId: String? = null
        var goodsId: String? = null
        var goodsName: String? = null
        var goodsNo: String? = null
        var unit: String? = null
        var number: String? = null
        var content: String? = null
        var orderNumber: String? = null
        var endQrCode: String? = null
        var startQrCode: String? = null
        var sapMaterialBatchNo: String? = null
        var state: String? = null
        var orderId: String? = null
        var boxQrCode: List<String>? = null


        var warehouseList: List<UserInfo.WarehouseListBean>? = null

    }

}
