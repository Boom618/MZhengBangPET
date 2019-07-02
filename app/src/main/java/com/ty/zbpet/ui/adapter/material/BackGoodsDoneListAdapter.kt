package com.ty.zbpet.ui.adapter.material

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/26.
 *
 *
 * 采购退货 已办列表
 */
class BackGoodsDoneListAdapter(context: Context, layoutId: Int, datas: List<MaterialList.ListBean>)
    : CommonAdapter<MaterialList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: MaterialList.ListBean, position: Int) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_voucher_no, list.materielVoucherNo)
                .setText(R.id.tv_supplier, list.supplierName)
                .setText(R.id.tv_date, list.outTime)
    }

}
