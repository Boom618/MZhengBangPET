package com.ty.zbpet.ui.adapter.material

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/26.
 * 销售出库 已办
 *
 *
 * 采购退货 已办列表
 */
class SaleDoneListAdapter(context: Context, layoutId: Int, datas: List<MaterialList.ListBean>)
    : CommonAdapter<MaterialList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: MaterialList.ListBean, position: Int) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_supplier, "供应商名称：${list.supplierName}")
                .setText(R.id.tv_date, list.outTime)
    }

}
