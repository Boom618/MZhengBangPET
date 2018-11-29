package com.ty.zbpet.ui.adapter.material

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.PickOutDoneDetailsData
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 * 领料出库 已办详情
 */
class PickingDoneDetailAdapter(context: Context, layoutId: Int, datas: List<PickOutDoneDetailsData.ListBean>)
    : CommonAdapter<PickOutDoneDetailsData.ListBean>(context, layoutId, datas)  {


    override fun convert(holder: ViewHolder, list: PickOutDoneDetailsData.ListBean, position: Int) {

        holder.setText(R.id.tv_name, list.materialName)
                .setText(R.id.tv_num, list.orderNumber + "  " + list.unitS)
                .setText(R.id.tv_box_num, "含量：" + list.concentration!!)
                .setText(R.id.tv_box_num_unit, "ZKG ：！ ")
                .setText(R.id.bulk_num, "库存量：！ ")
    }
}