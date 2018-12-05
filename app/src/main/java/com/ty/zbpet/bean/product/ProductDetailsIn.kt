package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/25.
 * 产品详情 bean
 */
class ProductDetailsIn {


    var list: List<ListBean>? = null

    class ListBean {
        /**
         * sapOrderNo : SAP0001
         * goodsName : 正邦_精灵火（40+30）g*100
         * goodsId : 20
         * goodsNo : 90000933
         * unitS : 瓶
         * orderNumber : 100
         * warehouseList : [{"warehouseId":"3","warehouseName":"仓库002"},{"warehouseId":"10","warehouseName":"仓库001"}]
         */

        var sapOrderNo: String? = null
        var goodsName: String? = null
        var goodsId: String? = null
        var goodsNo: String? = null
        var unitS: String? = null
        var orderNumber: String? = null
        var warehouseList: List<WarehouseListBean>? = null

        class WarehouseListBean {
            /**
             * warehouseId : 3
             * warehouseName : 仓库002
             */

            var warehouseId: String? = null
            var warehouseNo: String? = null
            var warehouseName: String? = null
        }
    }
}
