package com.ty.zbpet.bean

/**
 * @author TY on 2018/11/21.
 * 待办详情列表
 */
class PickOutTodoDetailsData {


    /**
     * sapOrderNo : SAP0001
     * list : [{"materialName":"原辅料名称","materialId":"15","materialNo":"原辅料编号","unitS":"瓶","orderNumber":"100","concentration":"80","supplierNo":"","supplierName":""},{"materialName":"原辅料名称","materialId":"14","materialNo":"原辅料编号","unit":"箱","orderNumber":"100","concentration":"75","supplierNo":"","supplierName":""},{"materialName":"原辅料名称","materialId":"13","materialNo":"原辅料编号","unit":"桶","orderNumber":"100","concentration":"90","supplierNo":"","supplierName":""},{"materialName":"原辅料名称","materialId":"12","materialNo":"原辅料编号","unit":"盒","orderNumber":"100","concentration":"50","supplierNo":"","supplierName":""}]
     */

    var sapOrderNo: String? = null
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * materialName : 原辅料名称
         * materialId : 15
         * materialNo : 原辅料编号
         * unitS : 瓶
         * orderNumber : 100
         * concentration : 80
         * supplierNo :
         * supplierName :
         */

        var materialName: String? = null
        var materialId: String? = null
        var materialNo: String? = null
        var unitS: String? = null
        var orderNumber: String? = null
        var concentration: String? = null
        var supplierNo: String? = null
        var supplierName: String? = null
    }
}
