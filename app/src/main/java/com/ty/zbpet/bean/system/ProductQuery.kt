package com.ty.zbpet.bean.system

/**
 * @author TY on 2019/3/22.
 * 成品溯源
 */
class ProductQuery {

    /**
     * qrCode : 180026147468
     * goodsNo : 90000973
     * goodsName : 正邦_保地乐500g*20
     * productionOrderNo : sap00001
     * productionBatchNo : 2018.2.2
     * productionDate : 2018-02-02 00:00:00
     * materialList : [{"materialNo":"10000298","materialName":"溶助剂_硫酸钠（元明粉/无水芒硝）","supplierNo":null,"supplierName":null,"sapBatchNo":"afdsgad"},{"materialNo":"10000298","materialName":"溶助剂_硫酸钠（元明粉/无水芒硝）","supplierNo":null,"supplierName":null,"sapBatchNo":"sdfgsfdg"}]
     */

    var qrCode: String? = null
    var goodsNo: String? = null
    var goodsName: String? = null
    var productionOrderNo: String? = null
    var productionBatchNo: String? = null
    var productionDate: String? = null
    var materialList: MutableList<MaterialListBean>? = null

    class MaterialListBean {
        /**
         * materialNo : 10000298
         * materialName : 溶助剂_硫酸钠（元明粉/无水芒硝）
         * supplierNo : null
         * supplierName : null
         * sapBatchNo : afdsgad
         */

        var materialNo: String? = null
        var materialName: String? = null
        var supplierNo: String? = null
        var supplierName: String? = null
        var sapBatchNo: String? = null
    }
}
