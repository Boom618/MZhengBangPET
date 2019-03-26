package com.ty.zbpet.ui.adapter.system

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.system.QualityCheckTodoDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.DataUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/12/13.
 *
 *
 * 质检 待办详情
 */
class QuaCheckTodoDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, dataBean: MaterialDetails.ListBean, position: Int) {


        holder.setText(R.id.tv_name, dataBean.materialName)
                .setText(R.id.tv_num, dataBean.inNumber + dataBean.unit)

    }


}
