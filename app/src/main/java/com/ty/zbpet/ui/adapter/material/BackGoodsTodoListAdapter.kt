package com.ty.zbpet.ui.adapter.material

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialTodoList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/26.
 *
 *
 * 采购退货 待办列表
 */
class BackGoodsTodoListAdapter(context: Context, layoutId: Int, datas: List<MaterialTodoList.ListBean>)
    : CommonAdapter<MaterialTodoList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: MaterialTodoList.ListBean, position: Int) {
        holder.setText(R.id.tv_operator, "退货")
                .setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_type, list.type)
                .setText(R.id.tv_supplier, list.supplierName)
                .setText(R.id.tv_date, list.orderTime)
                .setText(R.id.tv_status, list.state)
    }

}
