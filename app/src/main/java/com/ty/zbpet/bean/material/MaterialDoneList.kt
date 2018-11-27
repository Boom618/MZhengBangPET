package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/26.
 * 原料三个已办列表 bean
 */
class MaterialDoneList {


    /**
     * count : 2
     * list : [{"sapOrderNo":"SAP00011","mOutWarehouseOrderNo":null,"state":1,"type":21,"companyNo":"C000014","warehouseId":10,"recallOrderId":null,"userId":23,"creator":"test","outTime":"2018-09-18 10:56:00","createdAt":"2018-09-18T02:56:01.000Z","updatedAt":"2018-09-18T02:56:01.000Z","materialName":"黄豆\u2014\u2014批次1000,","mOutWarehouseOrderId":36,"supplierName":""},{"sapOrderNo":"SAP00011","mOutWarehouseOrderNo":null,"state":1,"type":21,"companyNo":"C000014","warehouseId":10,"recallOrderId":null,"userId":23,"creator":"test","outTime":"2018-09-18 11:01:26","createdAt":"2018-09-18T03:01:26.000Z","updatedAt":"2018-09-18T03:01:26.000Z","materialName":"黄豆\u2014\u2014批次5000,黄豆\u2014\u2014批次4000,","mOutWarehouseOrderId":37,"supplierName":""}]
     */

    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * sapOrderNo : SAP00011
         * mOutWarehouseOrderNo : null
         * state : 1
         * type : 21
         * companyNo : C000014
         * warehouseId : 10
         * recallOrderId : null
         * userId : 23
         * creator : test
         * outTime : 2018-09-18 10:56:00
         * createdAt : 2018-09-18T02:56:01.000Z
         * updatedAt : 2018-09-18T02:56:01.000Z
         * materialName : 黄豆——批次1000,
         * mOutWarehouseOrderId : 36
         * supplierName :
         */

        var sapOrderNo: String? = null
        var mOutWarehouseOrderNo: String? = null
        var state: String? = null
        var type: String? = null
        var companyNo: String? = null
        var warehouseId: String? = null
        var recallOrderId: String? = null
        var userId: String? = null
        var creator: String? = null
        var outTime: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
        var materialName: String? = null
        var mOutWarehouseOrderId: String? = null

    }
}
