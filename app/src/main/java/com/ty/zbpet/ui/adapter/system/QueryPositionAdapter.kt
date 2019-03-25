package com.ty.zbpet.ui.adapter.system

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.bean.system.ProductQuery
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2019/3/11.
 * 库位码查询
 */
class QueryPositionAdapter(context: Context, layout: Int, datas: MutableList<PositionCode.StockListBean>) :
        CommonAdapter<PositionCode.StockListBean>(context, layout, datas) {

    override fun convert(holder: ViewHolder, list: PositionCode.StockListBean, position: Int) {
        holder.setText(R.id.materials_name, "物料名称：${list.materialName}")
                .setText(R.id.into_batch, "入库批次：")
                .setText(R.id.supplier_name, "供应商：${list.supplierName}")
                .setText(R.id.content, "含量：${list.concentration}")
                .setText(R.id.number, "库存数量：${list.number}")
                .setText(R.id.unit, "单位：${list.unit}")

    }
}