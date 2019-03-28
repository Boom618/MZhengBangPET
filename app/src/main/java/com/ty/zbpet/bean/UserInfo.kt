package com.ty.zbpet.bean

import java.io.Serializable

/**
 * @author TY on 2018/12/4.
 *
 * 用户登录成功的信息
 */
class UserInfo : Serializable {

    /**
     * roleName 仓库管理员
     */
    var userName: String? = null
    var userId: String? = null
    var companyNo: String? = null
    var sessionId: String? = null
    var roleName: String? = null
    var roleId: List<String>? = null
    lateinit var warehouseList: MutableList<WarehouseListBean>

    /**
     * WarehouseListBean 不序列化, 导致 UserInfo 类不能缓存到文件
     */
    class WarehouseListBean : Serializable {
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
