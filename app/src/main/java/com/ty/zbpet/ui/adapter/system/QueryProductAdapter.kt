package com.ty.zbpet.ui.adapter.system

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.system.ProductQuery
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/11.
 * 成品追踪
 */
class QueryProductAdapter(context:Context, layout:Int, datas:MutableList<ProductQuery.MaterialListBean>):
        CommonAdapter<ProductQuery.MaterialListBean>(context,layout,datas) {

    override fun convert(holder: ViewHolder, list: ProductQuery.MaterialListBean, position: Int) {
        holder.setText(R.id.materials_name,"物料名称：${list.materialName}")
                .setText(R.id.supplier_name,"供应商：${list.supplierName}")
                .setText(R.id.content,"含量：")
                .setText(R.id.batch,"批次：${list.sapBatchNo}")

    }
}