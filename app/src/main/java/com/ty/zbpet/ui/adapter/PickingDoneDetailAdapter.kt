package com.ty.zbpet.ui.adapter

import android.content.Context
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


    override fun convert(holder: ViewHolder?, list: PickOutDoneDetailsData.ListBean, position: Int) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}