package com.ty.zbpet.bean.system

/**
 * @author  TY
 * @Date:   2019/4/30 16:37
 * @Description: 成品移库
 */
class ProductMove {

    var id: String? = null
    var time: String? = null
    // 冲销 or 移入目标仓库
    var orderId: String? = null
    var goodsNo: String? = null
    var goodsName: String? = null
    var warehouseNo: String? = null
    var inWarehouseNo: String? = null
    var outWarehouseNo: String? = null
    var list = mutableListOf<String>()
    var boxQrCode = mutableListOf<String>()
}