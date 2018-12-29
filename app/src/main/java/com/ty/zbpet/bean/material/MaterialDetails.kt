package com.ty.zbpet.bean.material

import java.util.ArrayList

/**
 * @author TY on 2018/12/29.
 *
 * 详情 + 保存
 */
class MaterialDetails {

    // 详情
    var sapOrderNo: String? = null

    // 保存
    var warehouseId: String? = null
    var outTime: String? = null
    var inTime: String? = null
    var remark: String? = null

    var list: ArrayList<ListBean>? = null

    class ListBean {

        // 待办详情
        var materialName: String? = null
        var materialId: String? = null
        var materialNo: String? = null
        var unitS: String? = null
        var orderNumber: String? = null
        var stockNumber: String? = null
        var concentration: String? = null
        var supplierNo: String? = null
        var supplierId: String? = null
        var positionNo: String? = null
        var supplierName: String? = null
        var ZKG: String? = null
        var tag: String? = null

        // 已办详情
        var id: String? = null
        var state: String? = null
        var giveNumber: String? = null
        var positionId: String? = null
        var warehouseId: String? = null
        var number: String? = null
        var sapMaterialBatchNo: String? = null

        // 待办保存
//        var positionId: String? = null
//        var supplierId: String? = null
//        var supplierNo: String? = null
//        var number: String? = null
//        var materialId: String? = null
//        var positionNo: String? = null
//        var sapMaterialBatchNo: String? = null
//        var concentration: String? = null
//        var ZKG: String? = null


        // 已办保存
//        MaterialDoneSave


    }


}