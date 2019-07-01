package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.SelectBatch
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.data.SharedP
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import org.greenrobot.eventbus.EventBus

/**
 * @author TY on 2018/11/22.
 *
 *
 * 领料出库 待办详情
 */
class PickingTodoDetailAdapter(private val context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        // 强制关闭复用
        holder.setIsRecyclable(false)
        val list = datas[position]
        val itemView = holder.itemView
        // 批次号
        val batchNo = itemView.findViewById<TextView>(R.id.tv_batch_no)
        // 已入库 标红
        when (list.isOut) {
            true -> {
                holder.setTextColor(R.id.tv_name, Color.RED)
            }
            false -> {
            }
        }

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

            val rbKG = itemView.findViewById<RadioButton>(R.id.rb_kg)
            val rbZKG = itemView.findViewById<RadioButton>(R.id.rb_zkg)
            val rbPC = itemView.findViewById<RadioButton>(R.id.rb_pc)
            if (list.unit.isNullOrEmpty()) {
                rbKG.isChecked = true
            } else {
                when (list.unit) {
                    "KG" -> rbKG.isChecked = true
                    "ZKG" -> rbZKG.isChecked = true
                    else -> {
                        rbPC.isChecked = true
                    }
                }
            }
            holder.setText(R.id.tv_name, list.materialName)
                    .setText(R.id.tv_num, list.requireNumber + "  " + list.unit)
                    .setText(R.id.tv_house_no, "仓库编号：${list.warehouseNo}")
                    .setText(R.id.tv_request_number, "需求数量：${list.requireNumber}")

        } else {
            val bundle = payloads[0] as Bundle
            val positionNo = bundle.getString("positionNo")
            etCode.setText(positionNo)
        }
        // 选择批次号
        batchNo.setOnClickListener { EventBus.getDefault().post(SelectBatch(position, batchNo)) }
    }

    override fun convert(holder: ViewHolder, list: MaterialDetails.ListBean, position: Int) {

    }

}
