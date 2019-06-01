package com.ty.zbpet.bean.system

/**
 * @author TY on 2019/4/2.
 * 盘点单据列表
 */
class ReceiptList {


    var count: Int = 0
    var list: MutableList<ListBean>? = null

    class ListBean {

        var id: String? = null
        var companyNo: String? = null
        var warehouseNo: String? = null
        var positionNo: String? = null
        var state: String? = null
        var type: String? = null
        var sapCheckNo: String? = null
        var content: String? = null
        var sapOrderNo: String? = null
        var number: String? = null
        var concentration: String? = null
        var skuName: String? = null
        var supplierName: String? = null
        // primaryNumber 原数量 checkNumber 盘点数量
        // sapCheckNo  盘点凭证   sapYear  盘点年度
        var sapYear: String? = null
        var checkNumber: String? = null
        var primaryNumber: String? = null
    }
}
