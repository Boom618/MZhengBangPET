package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 销售出库 待办详情
 */
class SaleTodoDetailAdapter(private val context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val list = datas[position]

        // 库位码
        val etCode = holder.itemView.findViewById<EditText>(R.id.et_code)
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.inputType = InputType.TYPE_NULL

        etCode.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            // 关闭软键盘
            ZBUiUtils.hideInputWindow(context, view)
            // 焦点改变 接口回调
            SharedP.putHasFocusAndPosition(view.context, hasFocus, position)
        }
        if (payloads.isEmpty()) {
            holder.setText(R.id.tv_name, list.materialName)
                    .setText(R.id.tv_num, list.number + "  " + list.unit)
                    .setText(R.id.tv_house_no, "仓库编号：${list.warehouseNo}")
                    .setText(R.id.tv_request_number, "需求数量：${list.number}")
                    .setText(R.id.tv_box_num_unit, "单位：${list.unit}")
        } else {
            val bundle = payloads[0] as Bundle
            val positionNo = bundle.getString("positionNo")
            etCode.setText(positionNo)
        }
    }

    override fun convert(holder: ViewHolder, list: MaterialDetails.ListBean, position: Int) {

    }

}