package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.view.View
import android.widget.CheckBox

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.util.SimpleCache
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 销售出库 已办详情
 */
class SaleDoneDetailAdapter(context: Context, private var voucherNo: String?, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: MaterialDetails.ListBean, position: Int) {

        holder.setVisible(R.id.tv_voucher_no, false)
        holder.setText(R.id.tv_voucher_no, "物料凭证号：$voucherNo")
                .setText(R.id.tv_document_no, "采购子单号：${listBean.sapOrderNo}")
                .setText(R.id.tv_material_name, "原辅料名称：${listBean.materialName}")
                .setText(R.id.tv_number, "发货数量：${listBean.giveNumber}${listBean.unit}")
                .setText(R.id.tv_solubility, "含量：${listBean.concentration}")
                .setText(R.id.tv_batch_number, "SAP 批次号：${listBean.sapMaterialBatchNo}")

        holder.itemView.findViewById<CheckBox>(R.id.check).setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> SimpleCache.putNumber(position.toString(), "1")
                false -> SimpleCache.putNumber(position.toString(), "0")
            }
        }

    }


}
