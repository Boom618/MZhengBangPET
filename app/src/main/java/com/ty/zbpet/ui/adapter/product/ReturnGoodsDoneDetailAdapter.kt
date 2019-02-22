package com.ty.zbpet.ui.adapter.product

import android.content.Context
import android.view.View

import com.ty.zbpet.R
import com.ty.zbpet.bean.product.ProductDetails
import com.ty.zbpet.util.DataUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 退货入库 已办详情
 */
class ReturnGoodsDoneDetailAdapter(context: Context, layoutId: Int, datas: List<ProductDetails.ListBean>) : CommonAdapter<ProductDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: ProductDetails.ListBean, position: Int) {
        // 共用 一个布局：下拉选择隐藏
        holder.itemView.findViewById<View>(R.id.tv_select_ware).visibility = View.GONE

        holder.setText(R.id.tv_name, list.goodsName)
                .setText(R.id.tv_number, "入库数量：" + DataUtils.string2Int(list.number))
                .setText(R.id.tv_start_code, "开始码：" + DataUtils.string2Int(list.startQrCode))
                .setText(R.id.tv_end_code, "结束码：" + DataUtils.string2Int(list.endQrCode))
                .setText(R.id.tv_sap, list.sapMaterialBatchNo)
                .setText(R.id.tv_num, list.number + "  " + list.unit)

    }


}
