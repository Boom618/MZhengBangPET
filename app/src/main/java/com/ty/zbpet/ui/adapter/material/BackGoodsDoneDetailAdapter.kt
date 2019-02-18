package com.ty.zbpet.ui.adapter.material

import android.content.Context

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 采购退货 已办详情
 */
class BackGoodsDoneDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, list: MaterialDetails.ListBean, position: Int) {

        holder.setText(R.id.tv_name, list.materialName)
                .setText(R.id.tv_num, list.giveNumber!! + list.unit!!)
                .setText(R.id.tv_box_num, "含量：" + list.concentration + "%")
                .setText(R.id.tv_box_num_unit, "ZKG:" + list.ZKG!!)
                .setText(R.id.bulk_num, "库存量：" + list.number)
                .setText(R.id.tv_code, "库位码：" + list.positionNo!!)
                .setText(R.id.tv_number, "出库数量：" + list.giveNumber!!)
                .setText(R.id.tv_batch_no, list.sapMaterialBatchNo)

    }


}
