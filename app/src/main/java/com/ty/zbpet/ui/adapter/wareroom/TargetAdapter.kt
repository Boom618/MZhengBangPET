package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.system.PositionCode
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.item_move_target_frg.view.*

/**
 * @author TY on 2019/3/18.
 * 目标库位码
 */
class TargetAdapter(context: Context, layout: Int, datas: MutableList<PositionCode.StockListBean>)
    : CommonAdapter<PositionCode.StockListBean>(context, layout, datas) {

    private var selected = -1

    fun setSelection(position: Int) {
        this.selected = position
        notifyDataSetChanged()
    }

    override fun convert(holder: ViewHolder, list: PositionCode.StockListBean, position: Int) {

        holder.setText(R.id.tv_type, "库位类型：${list.type}")
                .setText(R.id.tv_inventory_sap, "库存批次号：${list.sapBatchNo}")
                .setText(R.id.tv_material_name, "原辅料名称：${list.materialName}")
                .setText(R.id.tv_supplier_name, "供应商名称：${list.supplierName}")
                .setText(R.id.tv_number, "库存数量：${list.number}")
                .setText(R.id.tv_content, "含量：${list.concentration}")
                .setText(R.id.tv_former, "原库位码：${list.positionNo}")

        when (selected == position) {
            true -> holder.itemView.check.isChecked = true
            false -> holder.itemView.check.isChecked = false
        }

    }

}