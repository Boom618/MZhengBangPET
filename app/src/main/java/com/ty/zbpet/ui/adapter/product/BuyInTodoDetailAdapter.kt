package com.ty.zbpet.ui.adapter.product

import android.content.Context
import android.view.View
import android.widget.EditText

import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 外采入库 待办详情
 */
class BuyInTodoDetailAdapter(context: Context, layoutId: Int, datas: List<ProductDetails.ListBean>) : CommonAdapter<ProductDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductDetails.ListBean, position: Int) {
        val warehouseName = list.warehouseList!![0].warehouseName

        holder.setText(R.id.tv_name, list.goodsName)
                .setText(R.id.tv_num, "/" + list.unitS!!)
                .setText(R.id.tv_select_ware, warehouseName)


    }


}