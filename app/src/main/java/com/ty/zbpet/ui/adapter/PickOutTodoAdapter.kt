package com.ty.zbpet.ui.adapter

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.PickOutTodoData
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/21.
 *
 * 领料出库 待办 列表
 *
 */
class PickOutTodoAdapter(context: Context, layoutId: Int, datas: List<PickOutTodoData.ListBean>)
    : CommonAdapter<PickOutTodoData.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder?, list: PickOutTodoData.ListBean, position: Int) {

        holder!!.setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_status, list.state)
                .setText(R.id.tv_type, list.type)
                .setText(R.id.tv_out_message, list.receiveInfo)
                .setText(R.id.tv_supplier, list.supplierName)
                .setText(R.id.tv_date, list.orderTime)

    }
}