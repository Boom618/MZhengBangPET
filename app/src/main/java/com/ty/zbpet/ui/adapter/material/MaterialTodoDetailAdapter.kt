package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.util.*
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder


/**
 * @author TY on 2018/11/5.
 *
 *
 * 到货入库 （待办 详情）
 */
class MaterialTodoDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {


    /**
     * 重写带有 payloads 参数的 onBindViewHolder 方法, 才能配合 DiffUtil 正常使用
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val info = datas[position]

        val itemView = holder.itemView


        // 库位码
        val etCode = itemView.findViewById<EditText>(R.id.et_code)
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.inputType = InputType.TYPE_NULL

        etCode.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            // 关闭软键盘
            ZBUiUtils.hideInputWindow(view.context, view)
            SharedP.putHasFocusAndPosition(view.context,hasFocus,position)
        }
        if (payloads.isEmpty()) {
            val radioButton = itemView.findViewById<RadioButton>(R.id.rb_kg)
            radioButton.isChecked = true
            val tvName = itemView.findViewById<TextView>(R.id.tv_name)
            tvName.text = info.materialName

            val tvNum = itemView.findViewById<TextView>(R.id.tv_num)
            tvNum.text = info.orderNumber + info.unit

        } else {
            val bundle = payloads[0] as Bundle
            val positionNo = bundle.getString("positionNo")
            etCode.setText(positionNo)
        }

    }

    override fun convert(holder: ViewHolder, info: MaterialDetails.ListBean, position: Int) {

    }

}
