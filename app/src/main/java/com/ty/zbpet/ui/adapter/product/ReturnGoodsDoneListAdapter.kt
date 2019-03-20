package com.ty.zbpet.ui.adapter.product

import android.content.Context
import android.view.View

import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * 退货入库 已办列表
 * @author TY
 */
class ReturnGoodsDoneListAdapter(context: Context, layoutId: Int, datas: List<ProductList.ListBean>) : CommonAdapter<ProductList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductList.ListBean, position: Int) {
        // 退货不需要显示 成品信息

        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_client_msg, list.customerInfo)
                .setText(R.id.tv_send_msg, list.backInfo)
                .setText(R.id.tv_date, list.inTime)
                .setText(R.id.tv_status, list.state)
    }

}
