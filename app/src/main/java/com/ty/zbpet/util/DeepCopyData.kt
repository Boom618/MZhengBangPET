package com.ty.zbpet.util

import com.ty.zbpet.bean.material.MaterialDetails

/**
 * @author TY on 2019/1/3.
 *
 * 深拷贝数据
 */
object DeepCopyData {

    /**
     * 拷贝数据,在 DiffUtil 中使用
     */
    fun deepCopyList(item: MutableList<MaterialDetails.ListBean>): MutableList<MaterialDetails.ListBean> {

        val listBean = mutableListOf<MaterialDetails.ListBean>()

        for (it in item) {
            val item = MaterialDetails.ListBean()

            item.id = it.id
            item.concentration = it.concentration
            item.giveNumber = it.giveNumber
            item.orderNumber = it.orderNumber
            item.materialName = it.materialName
            item.materialId = it.materialId
            item.positionNo = it.positionNo
            item.unitS = it.unitS
            item.ZKG = it.ZKG

            listBean.add(item)
        }

        return listBean

    }
}