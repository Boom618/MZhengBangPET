package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import android.widget.CheckBox
import com.ty.zbpet.R
import com.ty.zbpet.bean.system.ProMoveList
import com.ty.zbpet.bean.system.ProductMove
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.item_move_target_two_frg.view.*

/**
 * @author  TY
 * @Date:   2019/5/6 14:34
 * @Description: 成品移入目标仓库 和 冲销目标仓库
 */
class ProReversalTargetAdapter(context: Context, layout: Int, list: MutableList<ProMoveList.ListBean>)
    : CommonAdapter<ProMoveList.ListBean>(context, layout, list) {

    private var selected = -1

    fun setSelection(position: Int) {
        this.selected = position
        notifyDataSetChanged()
    }

    override fun convert(holder: ViewHolder, data: ProMoveList.ListBean, position: Int) {

        holder.setText(R.id.tv_goods_name, "商品名称：${data.goodsName}")
                .setText(R.id.tv_goods_no, "商品编号：${data.goodsNo}")
                .setText(R.id.tv_number, "冲销数量：${data.number}")

        when (selected == position) {
            true -> {
//               holder.itemView.findViewById<CheckBox>(R.id.check).isChecked = true
                holder.itemView.check.isChecked = true
            }
            false -> {
                holder.itemView.check.isChecked = false
            }
        }


    }

}