package com.ty.zbpet.ui.adapter.material

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/21.
 *
 * 领料出库 已办 列表
 *
 */
class PickOutDoneAdapter(context: Context, layoutId: Int, datas: List<MaterialList.ListBean>)
    : CommonAdapter<MaterialList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder?, list: MaterialList.ListBean, position: Int) {

        holder!!.setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_status, list.state)
                .setText(R.id.tv_type, list.type)
                .setText(R.id.tv_move_type, "移动类型 ？")
                .setText(R.id.tv_out_message, " 领料 ？")
                .setText(R.id.tv_supplier, list.materialName)
                .setText(R.id.tv_date, list.outTime)

    }
}