package com.ty.zbpet.ui.adapter.system

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.bean.system.ProductQuery
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/11.
 * 原辅料盘点
 */
class InventorySourceAdapter(context: Context, layout: Int, datas: MutableList<PositionCode.StockListBean>) :
        CommonAdapter<PositionCode.StockListBean>(context, layout, datas) {

    override fun convert(holder: ViewHolder, list: PositionCode.StockListBean, position: Int) {
        holder.setText(R.id.tv_material_name, "原辅料名称：${list.materialName}")
                .setText(R.id.position_no, "库位码号：${list.positionNo}")
                .setText(R.id.tv_sap, "SAP 批次号：${list.sapBatchNo}")
                .setText(R.id.tv_supplier_name, "供应商：${list.supplierName}")
                .setText(R.id.tv_content, "含量：${list.concentration}")
                .setText(R.id.tv_number, "库存数量：${list.number}")

    }
}