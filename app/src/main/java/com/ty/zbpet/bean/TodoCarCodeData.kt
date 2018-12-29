package com.ty.zbpet.bean

/**
 * @author TY on 2018/12/29.
 *
 * 待办详情中 库位码更新
 */
class TodoCarCodeData {

    var list: List<ListBean>? = null

    class ListBean {


        // MaterialDetailsIn.ListBean
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
    }



}