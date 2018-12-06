package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/30.
 * 生产入库 待办详情
 */
class ProductTodoDetails {


    var warehouseList: List<WarehouseListBean>? = null
    var list: List<ListBean>? = null

    class WarehouseListBean {
        /**
         * warehouseId : 3
         * warehouseName : 仓库002
         */

        var warehouseId: String? = null
        var warehouseName: String? = null
    }

    class ListBean {
        /**
         * sapOrderNo : SAP0001
         * goodsName : 产品名称
         * goodsId : 20
         * goodsNo : 产品编号
         * unitS : 瓶
         * orderNumber : 100
         */

        var sapOrderNo: String? = null
        var goodsName: String? = null
        var goodsId: String? = null
        var goodsNo: String? = null
        var unitS: String? = null
        var orderNumber: String? = null
        var sendNumber: String? = null

        // 退货入库 信息
        var surplusNumber: String? = null
        var warehouseId: String? = null
        var warehouseName: String? = null

    }
}
