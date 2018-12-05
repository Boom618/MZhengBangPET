package com.ty.zbpet.bean

/**
 * @author TY on 2018/12/4.
 *
 * 用户登录成功的信息
 */
class UserInfo {

    /**
     * userName : test
     * userId : 23
     * roleId : [1,2]
     * companyNo : C000014
     * warehouseList : [{"warehouseId":3,"warehouseNo":"CK201808000003","warehouseName":"仓库002"},{"warehouseId":10,"warehouseNo":"CK201808000008","warehouseName":"仓库001"}]
     */

    var userName: String? = null
    var userId: String? = null
    var companyNo: String? = null
    var roleId: String? = null
    var warehouseList: List<WarehouseListBean>? = null

    class WarehouseListBean {
        /**
         * warehouseId : 3
         * warehouseNo : CK201808000003
         * warehouseName : 仓库002
         */

        var warehouseId: String? = null
        var warehouseNo: String? = null
        var warehouseName: String? = null
    }
}
