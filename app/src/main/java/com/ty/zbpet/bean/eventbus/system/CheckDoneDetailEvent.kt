package com.ty.zbpet.bean.eventbus.system

/**
 * @author TY on 2019/3/26.
 * 质检已办详情
 */
class CheckDoneDetailEvent {


    /**
     * arrivalOrderNo : 4500022746
     * id : 4500022746
     * checkTime : 2019-03-26T06:09:00.000Z
     * pathList : ["192.168.11.2:3099/files/af48b4392c105b00e2006be7e554ef02.jpg","192.168.11.2:3099/files/6374cc6b799e214ce21c4bf98e2d9225.jpg"]
     * checkReportList : [{"id":65,"checkID":63,"companyNo":null,"arrivalOrderNo":"4500022746","materialNo":"10024035","materialName":"正邦_泰美利不干胶贴（综合版）","checkNum":null,"percent":0.5,"unit":"PC","creator":null,"createdAt":"2019-03-26T06:09:49.000Z","updatedAt":"2019-03-26T06:09:49.000Z"},{"id":66,"checkID":63,"companyNo":null,"arrivalOrderNo":"4500022746","materialNo":"10024034","materialName":"汇5-15小彩盒","checkNum":null,"percent":0.8,"unit":"PC","creator":null,"createdAt":"2019-03-26T06:09:49.000Z","updatedAt":"2019-03-26T06:09:49.000Z"}]
     */

    var arrivalOrderNo: String? = null
    var id: String? = null
    var checkTime: String? = null
    var pathList: MutableList<String>? = null
    var checkReportList: List<CheckReportListBean>? = null

    class CheckReportListBean {

        var id: Int = 0
        var checkID: Int = 0
        var companyNo: Any? = null
        var arrivalOrderNo: String? = null
        var materialNo: String? = null
        var materialName: String? = null
        var checkNum: Any? = null
        var percent: Double = 0.toDouble()
        var unit: String? = null
        var creator: Any? = null
        var createdAt: String? = null
        var updatedAt: String? = null
    }
}
