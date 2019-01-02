package com.ty.zbpet.ui.adapter.product

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductList
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * 生产入库 待办列表
 * @author TY
 */
class ProductTodoListAdapter(context: Context, layoutId: Int, datas: List<ProductList.ListBean>) : CommonAdapter<ProductList.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductList.ListBean, position: Int) {
        holder.setText(R.id.tv_operator, "入库")
                .setText(R.id.tv_no, list.sapOrderNo)
                .setText(R.id.tv_produce_msg, list.productInfo)
                .setText(R.id.tv_date, list.orderTime)
                .setText(R.id.tv_status, list.state)
    }

}
