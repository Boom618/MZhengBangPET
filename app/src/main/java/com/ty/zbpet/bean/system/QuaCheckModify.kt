package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/17.
 *
 * 质检修改
 */
class QuaCheckModify {


    var materialCheckReportInfos: List<MaterialCheckReportInfosBean>? = null

    class MaterialCheckReportInfosBean {
        /**
         * arrivalOrderNo : 到货单号
         * checkDesc : 质检描述
         * checkTime : 质检时间
         * creator : 创建者
         * companyNo : 企业编号
         * fileName : 0d43f2c6a15f2587f81d23e6e3a2e5ae.jpg,da5c82971d620334025195f262733812.png(多个图片的名称以逗号隔开)
         * materialName : 原料名称
         * materialNo : 原料编号
         * percent : 含量(%)为正数且不大于100
         */

        var arrivalOrderNo: String? = null
        var checkDesc: String? = null
        var checkTime: String? = null
        var creator: String? = null
        var companyNo: String? = null
        var fileName: String? = null
        var materialName: String? = null
        var materialNo: String? = null
        var percent: String? = null
    }
}
