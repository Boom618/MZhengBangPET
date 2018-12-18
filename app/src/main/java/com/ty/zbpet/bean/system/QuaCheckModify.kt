package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/17.
 *
 * 质检修改（待办保存）
 */
class QuaCheckModify {


    var materialCheckReportInfo: MaterialCheckReportInfoBean? = null
    var materialInfos: List<MaterialInfosBean>? = null

    class MaterialCheckReportInfoBean {
        /**
         * checkDesc : 质检描述
         * checkTime : 质检时间
         * companyNo : 企业编号
         * creator : 创建者
         * fileName : 0d43f2c6a15f2587f81d23e6e3a2e5ae.jpg,da5c82971d620334025195f262733812.png(多个图片的名称以逗号隔开)
         */

        var checkDesc: String? = null
        var checkTime: String? = null
        var companyNo: String? = null
        var creator: String? = null
        var fileName: String? = null
    }

    class MaterialInfosBean {
        /**
         * arrivalOrderNo : 到货单号
         * checkNum : 质检数量
         * materialName : 原料名称
         * materialNo : 原料编号
         * percent : 含量(%)为正数且不大于100
         * unit : 单位
         */

        var arrivalOrderNo: String? = null
        var checkNum: String? = null
        var materialName: String? = null
        var materialNo: String? = null
        var percent: String? = null
        var unit: String? = null
    }
}
