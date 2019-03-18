package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import com.ty.zbpet.R
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/18.
 * 目标库位码
 */
class TargetAdapter(context: Context, layout: Int, datas: MutableList<String>)
    : CommonAdapter<String>(context, layout, datas) {

    override fun convert(holder: ViewHolder, t: String?, position: Int) {

        holder.setText(R.id.tv_inventory_sap, "库存批次号：")
                .setText(R.id.tv_material_name, "原辅料名称：")
                .setText(R.id.tv_supplier_name, "供应商名称：")
                .setText(R.id.tv_number, "库存数量：")
                .setText(R.id.tv_content, "含量：")
                .setText(R.id.tv_former, "原库位码：")

    }

}