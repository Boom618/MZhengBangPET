package com.ty.zbpet.bean.eventbus.system

/**
 * @author TY on 2018/12/18.
 * 个人中心
 */
class PersonCenterEvent {



    var userInfo: UserInfoBean? = null
    var warehouseList: WarehouseListBeanX? = null
    var companyInfo: CompanyInfoBean? = null

    class UserInfoBean {
        /**
         * id : 36
         * name : lq
         * userName : 13707056624
         */

        var id: Int = 0
        var name: String? = null
        var userName: String? = null
    }

    class WarehouseListBeanX {
        var warehouseList: List<WarehouseListBean>? = null

        class WarehouseListBean {
            /**
             * warehouseId : 1
             * warehouseNo : 0001
             * warehouseName : 高安原药库-正邦
             */

            var warehouseId: Int = 0
            var warehouseNo: String? = null
            var warehouseName: String? = null
        }
    }

    class CompanyInfoBean {

        var companyNo: String? = null
        var companyName: String? = null
        var companyAddress: String? = null
    }
}
