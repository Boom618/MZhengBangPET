package com.ty.zbpet.bean.system

/**
 * @author TY on 2018/12/12.
 *
 * 质检待办详情
 */
class QualityCheckTodoDetails{

    /**
     * arrivalOrderNo : 201808071024
     * materialName : 原料1
     * materialNo : 001
     * fileName : bafd595268f73f8f96ef4ff6fccddc9f.jpg,adea2a15dc2f9364f2efee2e6a4e9f96.jpg
     * pathList : ["/files/bafd595268f73f8f96ef4ff6fccddc9f.jpg","/files/bafd595268f73f8f96ef4ff6fccddc9f.jpg"]
     */


    var list: List<DataBean>? = null

    class DataBean{

        var arrivalOrderNo: String? = null
        var materialName: String? = null
        var materialNo: String? = null
        var fileName: String? = null
        var imageList: List<ImageBean>? = null
    }
    class ImageBean {
        var image: String? = null
    }
}
