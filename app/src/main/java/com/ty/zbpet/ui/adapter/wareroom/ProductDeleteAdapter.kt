package com.ty.zbpet.ui.adapter.wareroom

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.AdapterButtonClick
import com.ty.zbpet.bean.eventbus.system.DeleteCheckMessage
import com.ty.zbpet.bean.system.ProductInventorList
import com.ty.zbpet.bean.system.ReceiptList
import com.ty.zbpet.constant.CodeConstant
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import org.greenrobot.eventbus.EventBus

/**
 * @author TY on 2019/3/18.
 * 成品单据删除 列表
 */
class ProductDeleteAdapter(context: Context, layout: Int, datas: MutableList<ReceiptList.ListBean>)
    : CommonAdapter<ReceiptList.ListBean>(context, layout, datas) {

    override fun convert(holder: ViewHolder, list: ReceiptList.ListBean, position: Int) {

        holder.setText(R.id.sap_no, "库存批次号：${list.sapCheckNo}")
                .setText(R.id.order_type, "${list.type}")
                .setText(R.id.tv_material_name, "原辅料名称：${list.skuName}")
                .setText(R.id.tv_sap_year, "盘点年度：${list.sapYear}")
                .setText(R.id.tv_number, "盘点数量：${list.checkNumber}")
                .setText(R.id.tv_content, "含量：${list.concentration}")
                .setText(R.id.tv_actual_number, "原数量：${list.primaryNumber}")

        holder.itemView.findViewById<TextView>(R.id.tv_operator).setOnClickListener {
            EventBus.getDefault().post(DeleteCheckMessage(list.id, list.sapCheckNo))
        }


    }

}