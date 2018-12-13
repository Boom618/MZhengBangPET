package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/12.
 *
 * 质检已办详情
 */
class QualityCheckDoneDetails {

    var list: List<ListBean>? = null

    class ListBean {

        var sapOrderNo: String? = null
        var state: String? = null
        var type: String? = null
        var supplierName: String? = null
        var supplierId: String? = null
        var supplierNo: String? = null
        var receiveInfo: String? = null
        var productInfo: String? = null
        var customerInfo: String? = null
        var goodsInfo: String? = null
        var backInfo: String? = null
        var orderTime: String? = null
        var imageList: List<ImageBean>? = null
    }

    class ImageBean {
        var image: String? = null
    }
}
