package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/25.
 * 产品详情 bean 四个待办
 */
class ProductDetailsIn {


    var list: List<ListBean>? = null

    class ListBean {
        /**
         * sapOrderNo : SAP0001
         * goodsName : 原辅料名称
         * goodsId : 20
         * goodsNo : 原辅料编号
         * unitS : 瓶
         * orderNumber : 100
         */

        var sapOrderNo: String? = null
        var goodsName: String? = null
        var goodsId: String? = null
        var goodsNo: String? = null
        var unitS: String? = null
        var orderNumber: String? = null
    }
}
