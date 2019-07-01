package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/27.
 * 原料详情 bean 已办保存
 */
class MaterialDoneSave {

    var list: ArrayList<ListBean>? = null

    var warehouseId: String? = null
    var sapMaterialBatchNo: String? = null
    var orderId: String? = null
    var moveType: String? = null
    var deleteSign = false
    var sapOrderNo: String? = null
    var supplierName: String? = null
    var positionId: String? = null
    var outTime: String? = null
    var remark: String? = null

    class ListBean{
        var id: String? = null
        var orderId: String? = null
        var backNum: String? = null
        var giveNumber: String? = null
    }


}
