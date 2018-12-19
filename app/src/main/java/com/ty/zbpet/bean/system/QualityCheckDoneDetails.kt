package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/12.
 *
 * 质检已办详情
 */
class QualityCheckDoneDetails {

    var count: String? = null
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * id : 37
         * checkBatchNo : null
         * companyNo : null
         * arrivalOrderNo : 201808071024
         * materialNo : 001
         * materialName : 原料1
         * percent : 80
         * positionNo : null
         * supplierNo : null
         * fileName : 7831f527657ef9a76d88824ee552b381.JPEG
         * state : null
         * checkTime : null
         * checkDesc : null
         * creator : null
         * createdAt : 2018-12-18T07:24:51.000Z
         * updatedAt : 2018-12-18T07:24:51.000Z
         * pathList : ["\\files\\7831f527657ef9a76d88824ee552b381.JPEG"]
         */

        var id: String? = null
        var checkBatchNo: String? = null
        var companyNo: String? = null
        var arrivalOrderNo: String? = null
        var materialNo: String? = null
        var materialName: String? = null
        var percent: String? = null
        var positionNo: String? = null
        var supplierNo: String? = null
        var fileName: String? = null
        var state: String? = null
        var checkTime: String? = null
        var checkDesc: String? = null
        var creator: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
        var pathList: List<String>? = null
    }
}
