package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.system.PositionCode
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/18.
 * 原辅料库位冲销
 */
class MaterialReversalAdapter(context: Context, layout: Int, datas: MutableList<PositionCode.StockListBean>)
    : CommonAdapter<PositionCode.StockListBean>(context, layout, datas) {

    override fun convert(holder: ViewHolder, list: PositionCode.StockListBean, position: Int) {

        holder.setText(R.id.tv_position, "当前库位：${list.positionNo}")
                .setText(R.id.tv_material_name, "原辅料名称：${list.materialName}")
                .setText(R.id.tv_supplier_name, "供应商名称：${list.supplierName}")
                .setText(R.id.tv_number, "库存数量：${list.number}")
                .setText(R.id.tv_content, "含量：${list.concentration}")
                .setText(R.id.tv_sap, "SAP 批次号：")

    }

}