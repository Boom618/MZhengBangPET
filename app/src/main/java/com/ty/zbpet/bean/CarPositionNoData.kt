package com.ty.zbpet.bean

/**
 * @author TY on 2018/11/16.
 *
 * 库位码 校验
 */
class CarPositionNoData {



    var tag: String? = null
    var status: Int = 0
    var message: String? = null
    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {

        var id: String? = null
        var positionNo: String? = null
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var state: String? = null
        var type: String? = null
        var companyNo: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null

    }
}
