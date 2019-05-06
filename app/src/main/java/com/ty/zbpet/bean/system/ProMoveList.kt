package com.ty.zbpet.bean.system

/**
 * @author  TY
 * @Date:   2019/5/6 15:18
 * @Description: 目标仓库 拉取的列表
 */
class ProMoveList {

    var count: Int = 0
    var list: MutableList<ListBean>? = null

    class ListBean {

        var orderId: String? = null
        var detailsId: String? = null
        var goodsNo: String? = null
        var goodsName: String? = null
        var outWarehouseNo: String? = null
        var inWarehouseNo: String? = null
        var boxQrCode: String? = null
    }
}