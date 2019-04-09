package com.ty.zbpet.ui.adapter.product

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 发货出库 待办详情
 */
class SendOutTodoDetailAdapter(context: Context, layoutId: Int, datas: List<ProductDetails.ListBean>)
    : CommonAdapter<ProductDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductDetails.ListBean, position: Int) {
        holder.setText(R.id.tv_name, list.goodsName)
//                .setText(R.id.tv_select_ware, "所在仓库：" + list.warehouseNo)
                .setText(R.id.tv_send_number, "发货数量：" + list.number)
                .setText(R.id.tv_num, list.number + list.unit)
                .setText(R.id.btn_binding_code, "箱码出库")

    }


}
