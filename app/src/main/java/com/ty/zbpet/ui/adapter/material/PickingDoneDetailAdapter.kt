package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.view.View
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 * 领料出库 已办详情
 */
class PickingDoneDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas)  {

    override fun convert(holder: ViewHolder, listBean: MaterialDetails.ListBean, position: Int) {

        holder.setText(R.id.tv_document_no, "生产/预留单号：${listBean.materialNo}")
                .setText(R.id.tv_material_name,"原辅料名称：${listBean.materialName}")
                .setText(R.id.tv_number, "出库数量：${listBean.giveNumber} ${listBean.unit}")
                .setText(R.id.tv_solubility, "含量：${listBean.concentration}%")
        holder.itemView.findViewById<TextView>(R.id.tv_batch_number).visibility = View.GONE
    }
}