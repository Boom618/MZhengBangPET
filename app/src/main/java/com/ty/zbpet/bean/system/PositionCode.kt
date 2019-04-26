package com.ty.zbpet.bean.system

/**
 * @author TY on 2019/3/22.
 * 库位码查询
 */
class PositionCode {

    /**
     * positionNo : Y00001
     * type : 单批
     * stockList : [{"materialName":"原药_阿维菌素","materialNo":"10000066","supplierNo":"121321","supplierName":"测试","concentration":0.2,"number":478,"unit":"KG"}]
     * sum : 478
     */

    var positionNo: String = ""
    var warehouseNo: String = ""
    var count: String? = null
    var type: String? = null
    var time: String? = null
    var sum: String? = null
    // 溯源
    var list: MutableList<StockListBean>? = null

    class StockListBean {
        /**
         * materialName : 原药_阿维菌素
         * materialNo : 10000066
         * supplierNo : 121321
         * supplierName : 测试
         * concentration : 0.2
         * number : 478
         * unit : KG
         */

        var stockId: String? = null
        var positionNo: String? = null
        var sapOrderNo: String? = null
        var materialName: String? = null
        var materialNo: String? = null
        var supplierNo: String? = null
        var sapBatchNo: String? = null
        var supplierName: String? = null
        var concentration: String? = null
        var number: String? = null
        var moveNumber: String? = null
        var checkNumber: String? = null
        var unit: String? = null

        // 目标库位
        var id: String? = null
        var type: String? = null
    }
}
