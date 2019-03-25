package com.ty.zbpet.ui.adapter.system

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.bean.system.QualityCheckTodoList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * 质检 已办列表
 *
 * @author TY
 */
class QuaCheckDoneListAdapter(context: Context, layoutId: Int, datas: List<MaterialList.ListBean>) : CommonAdapter<MaterialList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: MaterialList.ListBean, position: Int) {
        holder.setText(R.id.tv_operator, "修改")
                .setText(R.id.tv_no, list.arrivalOrderNo)
                .setText(R.id.tv_date, list.inTime)
                .setText(R.id.tv_type, list.type)
                .setText(R.id.tv_status, list.state)
    }

}
