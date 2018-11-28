package com.ty.zbpet.bean

/**
 * @author TY on 2018/11/16.
 *
 * 库位码 校验
 */
class CarPositionNoData {


    /**
     * tag : success
     * status : 100
     * message :
     * count : 25
     * list : [{"id":1,"positionNo":"P000001","warehouseId":"10","warehouseNo":"CK201808000008","state":1,"type":3,"companyNo":"C000014","createdAt":"2018-09-01T07:59:12.000Z","updatedAt":"2018-09-13T02:20:33.000Z","warehouse.id":10,"warehouse.warehouseName":"仓库001","commonvalue.commonNo":"C02","commonvalue.commonValueNo":3,"commonvalue.commonValueName":"卖场1-库位类型"}]
     */

    var tag: String? = null
    var status: Int = 0
    var message: String? = null
    var count: Int = 0
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * id : 1
         * positionNo : P000001
         * warehouseId : 10
         * warehouseNo : CK201808000008
         * state : 1
         * type : 3
         * companyNo : C000014
         * createdAt : 2018-09-01T07:59:12.000Z
         * updatedAt : 2018-09-13T02:20:33.000Z
         *
         * warehouse.id : 10
         * warehouse.warehouseName : 仓库001
         * commonvalue.commonNo : C02
         * commonvalue.commonValueNo : 3
         * commonvalue.commonValueName : 卖场1-库位类型
         */

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
