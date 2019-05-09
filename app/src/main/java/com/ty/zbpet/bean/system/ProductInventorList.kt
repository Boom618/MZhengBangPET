package com.ty.zbpet.bean.system

/**
 * @author TY on 2019/4/1.
 * 成品盘点 列表
 */
class ProductInventorList {

    /**
     * tag : success
     * status : 100
     * message :
     * count : 693
     */

    var tag: String? = null
    var status: Int = 0
    var message: String? = null
    var count: Int = 0
    var list: MutableList<ListBean>? = null

    class ListBean {

        var id: String? = null
        var companyNo: String? = null
        var goodsNo: String? = null
        var goodsName: String? = null
        var registrationHolder: String? = null
        var unit: String? = null
        var state: String? = null

        // 成品移库
        var warehouseNo: String? = null
        var supplierNo: String? = null
        var supplierName: String? = null
        var positionNo: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
        var sum: String? = null


    }
}
