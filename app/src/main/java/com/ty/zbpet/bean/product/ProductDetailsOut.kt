package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/28.
 *
 * 产品详情 bean 四个已办
 */
class ProductDetailsOut {

    var list: List<ListBean>? = null

    class ListBean {
        /**
         * id : 119
         * inWarehouseOrderId : 76
         * positionId :
         * goodsId : 20
         * goodsName : 正邦_精灵火（40+30）g*100
         * goodsNo : 90000933
         * number : 2
         * productionDate :
         * expirationDate :
         * sapStorageNo :
         * state : 1
         * createdAt : 2018-09-25T10:11:50.000Z
         * updatedAt : 2018-09-25T10:11:50.000Z
         * boxQrCodeList : ["123456789012345678903","123456789012345678914"]
         */

        var id: Int = 0
        var inWarehouseOrderId: Int = 0
        var positionId: String? = null
        var goodsId: Int = 0
        var goodsName: String? = null
        var goodsNo: Int = 0
        var number: Int = 0
        var productionDate: String? = null
        var expirationDate: String? = null
        var sapStorageNo: String? = null
        var state: Int = 0
        var createdAt: String? = null
        var updatedAt: String? = null
        var unitS: String? = null
        var boxQrCodeList: List<String>? = null
    }
}