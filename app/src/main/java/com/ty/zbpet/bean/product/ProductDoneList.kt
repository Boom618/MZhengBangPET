package com.ty.zbpet.bean.product

/**
 * @author TY on 2018/11/25.
 *
 * 产品详情 bean 四个已办列表
 */
class ProductDoneList {


    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * id : 24
         * inWarhouseOrderNo : null
         * companyNo : C000014
         * sapOrderNo : SAP00002
         * state : 1
         * type : 1
         * inTime : 2018-09-14T06:42:46.000Z
         * productionBatchNo : null
         * recallOrderId : null
         * userId : 23
         * creator : test
         * createdAt : 2018-09-14T06:42:46.000Z
         * updatedAt : 2018-09-14T06:42:46.000Z
         */

        var id: String? = null
        var inWarhouseOrderNo: String? = null
        var companyNo: String? = null
        var sapOrderNo: String? = null
        var state: Int = 0
        var type: Int = 0
        var inTime: String? = null
        var productionBatchNo: String? = null
        var recallOrderId: String? = null
        var userId: Int = 0
        var creator: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
    }
}
