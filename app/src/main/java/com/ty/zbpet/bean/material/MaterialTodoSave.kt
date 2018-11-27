package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/27.
 * 原料详情 bean 待办保存
 */
class MaterialTodoSave {


    /**
     * warehouseId : 3
     * outWarehouseTime : 2018-09-06
     * sapProcOrder : SAP00009
     * remark : 1
     * details : [{"positionId":"1","supplierId":"12","number":"200","materialId":"1","positionNo":"234567","sapMaterialBatchNo":"sap物料批次号","concentration":"80"},{"positionId":"4","supplierId":"12","number":"300","materialId":"12","positionNo":"234567","sapMaterialBatchNo":"sap物料批次号","concentration":"90"}]
     */

    var warehouseId: String? = null
    var outWarehouseTime: String? = null
    var sapOrderNo: String? = null
    var remark: String? = null
    var details: List<DetailsBean>? = null

    class DetailsBean {
        /**
         * positionId : 1
         * supplierId : 12
         * number : 200
         * materialId : 1
         * positionNo : 库位码
         * sapMaterialBatchNo : sap物料批次号
         * concentration : 80
         */

        var positionId: String? = null
        var supplierId: String? = null
        var number: String? = null
        var materialId: String? = null
        var positionNo: String? = null
        var sapMaterialBatchNo: String? = null
        var concentration: String? = null
    }
}
