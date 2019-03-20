package com.ty.zbpet.ui.adapter.product

import android.content.Context
import android.view.View
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 * 外采入库 已办详情
 */
class BuyInDoneDetailAdapter(context: Context, layoutId: Int, datas: List<ProductDetails.ListBean>)
    : CommonAdapter<ProductDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductDetails.ListBean, position: Int) {
        holder.setText(R.id.tv_document_no, "采购子单号：${list.sapOrderNo}")
                .setText(R.id.tv_material_name, "原辅料名称：${list.goodsName}")
                .setText(R.id.tv_number, "入库数量：${list.number}")
                .setText(R.id.tv_solubility, "含量：")
//                .setText(R.id.tv_sap, "SAP 物料：" + list.sapMaterialBatchNo)
//                .setText(R.id.tv_select_ware, "所在仓库：" + list.warehouseName)
//                .setText(R.id.tv_num, list.number + "  " + list.unit)

        holder.itemView.findViewById<TextView>(R.id.tv_batch_number).visibility = View.GONE

    }


}
