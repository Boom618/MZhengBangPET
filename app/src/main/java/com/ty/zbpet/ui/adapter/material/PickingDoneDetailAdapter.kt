package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.SimpleAdapter
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.util.SimpleCache
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 * 领料出库 已办详情
 */
class PickingDoneDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: MaterialDetails.ListBean, position: Int) {

        holder.setIsRecyclable(false)
        holder.setText(R.id.tv_voucher_no, "物料凭证号：${listBean.materielVoucherNo}")
                .setText(R.id.tv_document_no, "生产/预留单号：${listBean.materialNo}")
                .setText(R.id.tv_material_name, "原辅料名称：${listBean.materialName}")
                .setText(R.id.tv_number, "出库数量：${listBean.giveNumber}")
                .setText(R.id.tv_solubility, "含量：${listBean.concentration}")
        holder.itemView.findViewById<TextView>(R.id.tv_batch_number).visibility = View.GONE

        holder.itemView.findViewById<CheckBox>(R.id.check).setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> SimpleCache.putNumber(position.toString(), "1")
                false -> SimpleCache.putNumber(position.toString(), "0")
            }
        }
    }
}