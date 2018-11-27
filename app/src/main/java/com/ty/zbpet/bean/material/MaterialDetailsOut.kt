package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/25.
 * 原料详情 bean 已办
 */
class MaterialDetailsOut {


    var list: List<ListBean>? = null

    class ListBean {
        /**
         * id : 52
         * mOutWarehouseOrderNo :
         * mOutWarehouseOrderId : 36
         * state : 1
         * materialNo : GYS2018000020
         * materialId : 15
         * materialName : 黄豆——批次
         * concentration :
         * requiredNumber :
         * giveNumber : 1000
         * positionId : 12
         * supplierId : 12
         * warehouseId :
         * productionBatch :
         * expirationDate :
         * productionDate :
         * createdAt : 2018-09-18T02:56:01.000Z
         * updatedAt : 2018-09-18T02:56:01.000Z
         * unitS : 1
         */

        var id: Int = 0
        var mOutWarehouseOrderNo: String? = null
        var mOutWarehouseOrderId: Int = 0
        var state: Int = 0
        var materialNo: String? = null
        var materialId: Int = 0
        var materialName: String? = null
        var concentration: String? = null
        var requiredNumber: String? = null
        var giveNumber: Int = 0
        var positionId: Int = 0
        var supplierId: Int = 0
        var warehouseId: String? = null
        var productionBatch: String? = null
        var expirationDate: String? = null
        var productionDate: String? = null
        var createdAt: String? = null
        var updatedAt: String? = null
        var unitS: Int = 0
    }


}
