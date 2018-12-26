package com.ty.zbpet.ui.adapter.material

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialTodoList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/10/26.
 *
 *
 * 到货入库 Adapter
 */
class MaterialTodoAdapter(context: Context, layoutId: Int, datas: List<MaterialTodoList.ListBean>)
    : CommonAdapter<MaterialTodoList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: MaterialTodoList.ListBean, position: Int) {

        holder.setText(R.id.tv_no, listBean.sapOrderNo)
                .setText(R.id.tv_operator, "入库")
                .setText(R.id.tv_type, listBean.type)
                .setText(R.id.tv_status, listBean.state)
                .setText(R.id.tv_supplier, listBean.supplierName)
                .setText(R.id.tv_date, listBean.orderTime)

    }


}
