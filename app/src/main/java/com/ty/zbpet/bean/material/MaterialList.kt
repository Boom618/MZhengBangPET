package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/12/29.
 *
 * 列表 （已办和待办）
 *
 *
 */
class MaterialList {


    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {

        var id: String? = null
        var companyNo: String? = null
        var warehouseId: String? = null
        var supplierNo: String? = null
        var creatorNo: String? = null
        var arrivalOrderNo: String? = null
        var state: String? = null
        var goodsNo: String? = null
        var goodsName: String? = null
        var type: String? = null
        var sign: String? = null
        var inTime: String? = null
        var outTime: String? = null
        var checkTime: String? = null
        var sapOrderNo: String? = null
        var supplierName: String? = null
        var materialName: String? = null
        var orderId: String? = null

        var sapFirmNo: String? = null
        var content: String? = null

        // 待办
        var orderTime: String? = null

    }
}