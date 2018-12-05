package com.ty.zbpet.bean.material

/**
 * @author TY on 2018/11/27.
 * 原料详情 bean 已办保存
 */
class MaterialDoneSave {


    /**
     * warehouseId : 3
     * outTime : 2018-09-06
     * sapProcOrder : SAP00009
     * remark : 1
     * details : [{"positionId":"1","supplierId":"12","number":"200","materialId":"1","positionNo":"234567","sapMaterialBatchNo":"sap物料批次号","concentration":"80"},{"positionId":"4","supplierId":"12","number":"300","materialId":"12","positionNo":"234567","sapMaterialBatchNo":"sap物料批次号","concentration":"90"}]
     */

    var warehouseId: String? = null
    var sapMaterialBatchNo: String? = null
    var orderId: String? = null
    var sapProcOrder: String? = null
    var sapOrderNo: String? = null
    var positionId: String? = null
    var outTime: String? = null
    var remark: String? = null


}
