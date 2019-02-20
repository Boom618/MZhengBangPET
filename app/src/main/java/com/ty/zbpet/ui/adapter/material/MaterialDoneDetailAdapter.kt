package com.ty.zbpet.ui.adapter.material

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/14.
 * 已办详情
 */
class MaterialDoneDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: MaterialDetails.ListBean, position: Int) {

        holder.setText(R.id.tv_name, listBean.materialName)
                .setText(R.id.tv_num, listBean.number)
                .setText(R.id.tv_bulk, "含量：" + listBean.concentration + "%")
                .setText(R.id.tv_zkg, "ZKG：" + listBean.ZKG!!)
                .setText(R.id.tv_count_number, "入库数量：" + listBean.number!!)
                .setText(R.id.tv_sap, "sap 物料：" + listBean.sapMaterialBatchNo)
                .setText(R.id.tv_code, "库位码：" + listBean.positionNo!!)

    }

}
