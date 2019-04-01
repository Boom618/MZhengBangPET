package com.ty.zbpet.bean.system

/**
 * @author TY on 2019/4/1.
 * 成品盘点 列表
 */
class ProductInventorList {

    /**
     * tag : success
     * status : 100
     * message :
     * count : 693
     */

    var tag: String? = null
    var status: Int = 0
    var message: String? = null
    var count: Int = 0
    var list: MutableList<ListBean>? = null

    class ListBean {

        var id: Int = 0
        var creator: String? = null
        var modifer: String? = null
        var modifyTS: String? = null
        var archiveFlag: String? = null
        var archiveTS: String? = null
        var companyNo: String? = null
        var registrationHolder: String? = null
        var goodsNo: String? = null
        var goodsName: String? = null
        var commonName: String? = null
        var goodsTypeId: Int = 0
        var unit: String? = null
        var goodsSpec: String? = null
        var specCode: String? = null
        var registerCode: String? = null
        var registerTypeCode: Int = 0
        var productionType: Int = 0
        var licenceCode: String? = null
        var licenceCodeEndTime: String? = null
        var dosageForm: String? = null
        var goodsBrand: String? = null
        var goodsLevel: String? = null
        var goodsDesc: String? = null
        var goodsPeriod: String? = null
        var goodsComposition: String? = null
        var goodsEffect: String? = null
        var goodsAdaptCrowd: String? = null
        var goodsPreservatives: String? = null
        var goodsStorageMethod: String? = null
        var templateNo: String? = null
        var state: Int = 0
        var createdAt: String? = null
        var updatedAt: String? = null

    }
}
