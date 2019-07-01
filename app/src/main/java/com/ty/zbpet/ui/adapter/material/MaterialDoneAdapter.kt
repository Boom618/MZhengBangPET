package com.ty.zbpet.ui.adapter.material

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/13.
 *
 *
 * 原材料 已办 列表
 */
class MaterialDoneAdapter(context: Context, layoutId: Int, datas: List<MaterialList.ListBean>)
    : CommonAdapter<MaterialList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: MaterialList.ListBean, position: Int) {

        holder.setText(R.id.tv_no, listBean.sapOrderNo)
                .setText(R.id.tv_voucher_no, listBean.materielVoucherNo)
                .setText(R.id.tv_date, listBean.inTime)
                .setText(R.id.tv_supplier, listBean.supplierName)

    }
}
