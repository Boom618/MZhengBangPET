package com.ty.zbpet.bean.material

import java.util.ArrayList

/**
 * @author TY on 2018/12/29.
 *
 * 详情 + 保存
 */
class MaterialDetails {

    // 详情
    var sapOrderNo: String? = null

    // 保存
    var warehouseId: String? = null
    var warehouseNo: String? = null
    var content: String? = null
    var moveType: String? = null
    var supplierNo: String? = null
    var supplierName: String? = null
    var creatorNo: String? = null
    var outTime: String? = null
    var sign: String? = null
    var inTime: String? = null
    var remark: String? = null

    var list: ArrayList<ListBean>? = null

    class ListBean {

        // 待办详情
        var materialName: String? = null
        var materialId: String? = null
        var materialNo: String? = null
        var unit: String? = null
        var orderNumber: String? = null
        var concentration: String? = null
        var supplierNo: String? = null
        var sapOrderNo: String? = null
        var supplierId: String? = null
        var positionNo: String? = null
        var supplierName: String? = null
        var remarkSub: String? = null
        var ZKG: String? = null
        var tag: String? = null

        // 已办详情
        var id: String? = null
        var state: String? = null
        var giveNumber: String? = null
        var positionId: String? = null
        var warehouseId: String? = null
        var warehouseNo: String? = null
        var number: String? = null
        var sapMaterialBatchNo: String? = null
        var sapBatchNo: String? = null

        // 已办 保存
        var orderId: String? = null


        // 二版 入库新加字段
        var sapFirmNo: String? = null
        var content: String? = null
        var inNumber: String? = null

        // 二版 出库新加字段
        var requireNumber: String? = null

        // 二版 采购入库类型
        var deliveryOrderList: ArrayList<OrderList>? = null

        // 物料凭证号
        var materielVoucherNo: String? = null
        // 原药是否已入库操作
        var isOut = false
        // 冲销总量（领料出库）
        var backNum: String? = null

    }

    class OrderList {
        var deliveryOrderNo: String? = null
        var deliveryOrderLine: String? = null
        var sapBatchNo: String? = null
        var number: String? = null
        var unit: String? = null
    }


}