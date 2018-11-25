package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/25.
 * 原料详情 bean 三个待办 保存
 */
class MaterialDetailsIn {


    /**
     * warehouseId : 3
     * inStoreDate : 2018-09-06
     * sapProcOrder : SAP00009
     * remark : 备注
     * details : [{"positionId":"1","supplierId":"12","number":"200","sapMaterialBatchNo":"sap物料批次号","materialName":"原辅料名称","unitS":"箱","materialId":"1","orderNumber":"订单数量","concentration":"80"}]
     */

    var warehouseId: String? = null
    var inStoreDate: String? = null
    var sapProcOrder: String? = null
    var remark: String? = null
    var details: List<DetailsBean>? = null

    class DetailsBean {
        /**
         * positionId : 1
         * supplierId : 12
         * number : 200
         * sapMaterialBatchNo : sap物料批次号
         * materialName : 原辅料名称
         * unitS : 箱
         * materialId : 1
         * orderNumber : 订单数量
         * concentration : 80
         */

        var positionId: String? = null
        var supplierId: String? = null
        var number: String? = null
        var sapMaterialBatchNo: String? = null
        var materialName: String? = null
        var unitS: String? = null
        var materialId: String? = null
        var orderNumber: String? = null
        var concentration: String? = null
    }
}
